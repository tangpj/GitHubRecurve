package com.tangpj.repository.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.repository.db.RepoDao
import com.tangpj.repository.vo.RepoVo
import com.tangpj.recurve.apollo.LiveDataApollo

import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.mapper.getRepoDtoList
import com.tangpj.repository.mapper.mapRepoVo
import com.tangpj.repository.type.OrderDirection
import com.tangpj.repository.type.StarOrder
import com.tangpj.repository.type.StarOrderField
import org.threeten.bp.ZoneId
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
         val apolloClient: ApolloClient,
         val repoDao: RepoDao){

    private val repoRateLimiter = RateLimiter<StartRepositoriesQuery>(1, TimeUnit.SECONDS)

    fun loadStarRepos(login: String) =
            object : ItemKeyedBoundResource<String, RepoVo, StartRepositoriesQuery.Data>(){

                private var pageInfo: StartRepositoriesQuery.PageInfo? = null



                val order = StarOrder
                        .builder()
                        .field(StarOrderField.STARRED_AT)
                        .direction(OrderDirection.DESC).build()

                var query: StartRepositoriesQuery? = null

                override fun saveCallResult(item: StartRepositoriesQuery.Data) {
                    pageInfo = item.user?.starredRepositories?.pageInfo
                    Timber.d("saveCallResult, pageInfo = $pageInfo")
                    saveStarRepo(item)
                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        data == null || data.isEmpty() || repoRateLimiter.shouldFetch(query)

                override fun loadFromDb(): LiveData<List<RepoVo>>  {
                    val repoIds = repoDao.loadStarRepoResult(login)
                    return Transformations.switchMap(repoIds){
                        repoDao.loadRepoOrderById(it)
                    }
                }

                override fun hasNextPage(): Boolean {
                    return pageInfo?.isHasNextPage ?: false
                }

                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>): LiveData<ApiResponse<StartRepositoriesQuery.Data>> {
                    val initialQuery = StartRepositoriesQuery.builder()
                            .login(login)
                            .order(order).build()
                    query = initialQuery
                    Timber.d("createInitialCall, start first = ${params.requestedLoadSize}, hasNextPage = ${pageInfo?.isHasNextPage}")
                    val repoCall = apolloClient
                            .query(initialQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

                @SuppressLint("BinaryOperationInTimber")
                override fun createAfterCall(params: ItemKeyedDataSource.LoadParams<String>): LiveData<ApiResponse<StartRepositoriesQuery.Data>> {
                    val afterQuery = StartRepositoriesQuery.builder()
                            .login(login)
                            .startFirst(params.requestedLoadSize)
                            .after(pageInfo?.endCursor)
                            .order(order).build()
                    query = afterQuery

                    val repoCall = apolloClient
                            .query(afterQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

                override fun getKey(item: RepoVo): String = item.id

            }.asListing( Config(
                    pageSize = 30,
                    enablePlaceholders = false,
                    initialLoadSizeHint = 30 * 2))


    private fun saveStarRepo(data: StartRepositoriesQuery.Data){
        val userRepoResultList = mutableListOf<StarRepoResult>()
        val login = data.user?.login ?: ""
        val repoDtoList = data.getRepoDtoList { starredAt, repoDto ->
            val zoneId = ZoneId.systemDefault()

            userRepoResultList.add(StarRepoResult(
                    login,
                    repoDto.id,
                    starredAt.atZone(zoneId).toInstant().toEpochMilli()))
        }
        repoDao.insertUserRepoResult(userRepoResultList)
        saveRepoResult(repoDtoList.toList())
    }

    private fun saveRepoResult(repoDtoList: List<RepoDto>){
        if (repoDtoList.isEmpty()) {
            return
        }
        val repoVoList = repoDtoList.map { repoDto ->
            val languages = repoDto.languages?.nodes
            val languageDto = if (languages != null && languages.size > 0){
                languages[0].fragments.languageDto
            }else{
                null
            }
            repoDto.mapRepoVo(languageDto)

        }
        repoDao.insertRepos(repoVoList)
    }

}