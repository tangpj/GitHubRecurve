package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.domain.RepoFlag
import com.tangpj.repository.db.RepoDao
import com.tangpj.repository.vo.RepoVo
import com.tangpj.recurve.apollo.LiveDataApollo

import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.WatchRepositoriesQuery
import com.tangpj.repository.domain.UserRepoResult
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.mapper.getRepoDtoList
import com.tangpj.repository.mapper.mapRepoVo
import com.tangpj.repository.type.CustomType
import com.tangpj.repository.type.OrderDirection
import com.tangpj.repository.type.StarOrder
import com.tangpj.repository.type.StarOrderField
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
         val apolloClient: ApolloClient,
         val repoDao: RepoDao){

    private val repoRateLimiter = RateLimiter<String>(1, TimeUnit.MINUTES)

    fun loadStarRepos(login: String) =
            object : NetworkBoundResource<List<RepoVo>, StartRepositoriesQuery.Data>(){
                override fun saveCallResult(item: StartRepositoriesQuery.Data) {
                    val repoDtoList = item.getRepoDtoList()
                    repoDtoList?.let { saveRepoResult(login, RepoFlag.STAR, repoDtoList) }
                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        data == null || data.isEmpty() || repoRateLimiter.shouldFetch(login)

                override fun loadFromDb(): LiveData<List<RepoVo>>  {
                    val repoIds = repoDao.loadUserRepoResult(login, RepoFlag.STAR)
                    return Transformations.switchMap(repoIds){
                        repoDao.loadRepositories(it)
                    }
                }

                override fun createCall(): LiveData<ApiResponse<StartRepositoriesQuery.Data>> {
                    val order = StarOrder
                            .builder()
                            .field(StarOrderField.STARRED_AT)
                            .direction(OrderDirection.DESC).build()
                    val query = StartRepositoriesQuery.builder()
                            .login(login)
                            .order(order).build()
                    val repoCall = apolloClient
                            .query(query)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

            }.asLiveData()

    fun loadWatchRepo(login: String) =
            object : NetworkBoundResource<List<RepoVo>, WatchRepositoriesQuery.Data>(){
                override fun saveCallResult(item: WatchRepositoriesQuery.Data) {
                    val repoDtoList = item.getRepoDtoList()
                    repoDtoList?.let { saveRepoResult(login, RepoFlag.WATCH, repoDtoList) }
                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        data == null || data.isEmpty() || repoRateLimiter.shouldFetch(login)

                override fun loadFromDb(): LiveData<List<RepoVo>>  {
                    val repoIds = repoDao.loadUserRepoResult(login, RepoFlag.WATCH)
                    return Transformations.switchMap(repoIds){
                        repoDao.loadRepositories(it)
                    }
                }

                override fun createCall(): LiveData<ApiResponse<WatchRepositoriesQuery.Data>> {
                    val query = WatchRepositoriesQuery.builder().login(login).build()
                    val repoCall = apolloClient
                            .query(query)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

            }.asLiveData()


    private fun saveRepoResult(login: String, @RepoFlag repoFlag: Int, repoDtoList: List<RepoDto>){
        if (repoDtoList.isEmpty()) {
            return
        }
        val userRepoResultList = mutableListOf<UserRepoResult>()
        val repoVoList = repoDtoList.map { repoDto ->
            val languages = repoDto.languages?.nodes
            val languageDto = if (languages != null && languages.size > 0){
                languages[0].fragments.languageDto
            }else{
                null
            }
            userRepoResultList.add(UserRepoResult(login, repoDto.id, repoFlag))
            repoDto.mapRepoVo(languageDto)

        }
        repoDao.insertRepos(repoVoList)
        repoDao.insertUserRepoResult(userRepoResultList)
    }

}