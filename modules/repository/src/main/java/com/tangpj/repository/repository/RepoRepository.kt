package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.domain.RepoFlag
import com.tangpj.repository.db.RepoDao
import com.tangpj.repository.vo.RepoVo
import com.tangpj.recurve.apollo.LiveDataApollo

import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.StartReposioriesQuery
import com.tangpj.repository.domain.UserRepoResult
import com.tangpj.repository.mapper.RepoMapper
import com.tangpj.repository.mapper.mapRepoVo
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
         val apolloClient: ApolloClient,
         val repoDao: RepoDao){

    private val repoRateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(login: String) =
            object : NetworkBoundResource<List<RepoVo>, StartReposioriesQuery.Data>(){
                override fun saveCallResult(item: StartReposioriesQuery.Data) {
                    val repoMapper: RepoMapper = Mappers.getMapper(RepoMapper::class.java)
                    val repoEdges = item.user?.starredRepositories?.edges
                    val repoVoList = mutableListOf<RepoVo>()
                    val userRepoResultList = mutableListOf<UserRepoResult>()
                    repoEdges?.forEach { edge ->
                        val repoDto = edge.node.fragments.repoDto
                        val languages = repoDto.languages?.nodes
                        val languageDto = if (languages != null && languages.size > 0){
                            languages[0].fragments.languageDto
                        }else{
                            null
                        }
                        repoVoList.add(repoDto.mapRepoVo(repoMapper, languageDto))
                        userRepoResultList.add(UserRepoResult(login, repoDto.id, RepoFlag.STAR))
                    }
                    repoDao.insertRepos(repoVoList)
                    repoDao.insertUserRepoResult(userRepoResultList)
                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        data == null || data.isEmpty() || repoRateLimiter.shouldFetch(login)

                override fun loadFromDb(): LiveData<List<RepoVo>>  {
                    val repoIds = repoDao.loadUserRepoResult(login, RepoFlag.STAR)
                    val dbResult = MediatorLiveData<List<RepoVo>>()
                    dbResult.addSource(repoIds){
                        dbResult.value = repoDao.loadRepositories(it).value
                    }
                    return dbResult
                }


                override fun createCall(): LiveData<ApiResponse<StartReposioriesQuery.Data>> {
                    val query = StartReposioriesQuery.builder().login(login).build()
                    val repoCall = apolloClient
                            .query(query)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

            }.asLiveData()

}