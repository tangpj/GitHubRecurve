package com.tangpj.repository.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.repository.db.RepoDao
import com.tangpj.repository.vo.RepoVo
import com.tangpj.recurve.apollo.LiveDataApollo

import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.recurve.util.singelSwitchMap
import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.mapper.*
import com.tangpj.repository.type.OrderDirection
import com.tangpj.repository.type.StarOrder
import com.tangpj.repository.type.StarOrderField
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
         val apolloClient: ApolloClient,
         val repoDao: RepoDao){

    private val repoRateLimiter = RateLimiter<StartRepositoriesQuery>(1, TimeUnit.SECONDS)

    fun loadStarRepos(login: String) =
            object : ItemKeyedBoundResource<String, RepoVo, StartRepositoriesQuery.Data>(){

                private var repoResult: StarRepoResult? = null

                val order = StarOrder
                        .builder()
                        .field(StarOrderField.STARRED_AT)
                        .direction(OrderDirection.DESC).build()

                var query: StartRepositoriesQuery? = null

                override fun saveCallResult(item: StartRepositoriesQuery.Data) {
                    Timber.d("saveCallResult, pageInfo = ${repoResult?.pageInfo}")
                    saveStarRepo(item, repoResult)
                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        (data == null || data.isEmpty() || repoRateLimiter.shouldFetch(query))

                override fun loadFromDb(): LiveData<List<RepoVo>>  {
                    val repoResultLive = repoDao.loadStarRepoResult(login)
                    return repoResultLive.singelSwitchMap{
                        repoDao.loadRepoOrderById(it?.repoIds ?: emptyList())
                    }
                }

                override fun hasNextPage(): Boolean {
                    return repoResult?.pageInfo?.hasNextPage ?: false
                }

                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>): LiveData<ApiResponse<StartRepositoriesQuery.Data>> {
                    val initialQuery = StartRepositoriesQuery.builder()
                            .login(login)
                            .order(order).build()
                    query = initialQuery
                    Timber.d("createInitialCall, start first = ${params.requestedLoadSize}, hasNextPage = ${repoResult?.pageInfo?.hasNextPage}")
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
                            .after(repoResult?.pageInfo?.endCursor)
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


    private fun saveStarRepo(data: StartRepositoriesQuery.Data, starRepoResult: StarRepoResult?){
        repoDao.insertRepos((data.mapperToRepoVoList {
            val ids = mutableListOf<String>()
            starRepoResult?.let { result ->
                ids.addAll(result.repoIds)
            }
            ids.addAll(it.repoIds)
            repoDao.insertUserRepoResult(StarRepoResult(
                    login = it.login,
                    repoIds = ids,
                    pageInfo = it.pageInfo))
        }))
    }

}