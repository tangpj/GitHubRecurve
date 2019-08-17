package com.tangpj.repository.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.repository.vo.Repo
import com.tangpj.recurve.apollo.LiveDataApollo

import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.valueObject.result.StarRepoResult
import com.tangpj.repository.type.OrderDirection
import com.tangpj.repository.type.StarOrder
import com.tangpj.repository.type.StarOrderField
import com.tangpj.repository.ApolloStartRepositoriesQuery
import com.tangpj.repository.mapper.getPageInfo
import com.tangpj.repository.mapper.mapperToRepoVoList
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
         val apolloClient: ApolloClient,
         val repoDb: RepositoryDb){

    private val reposRateLimiter = RateLimiter<ApolloStartRepositoriesQuery>(1, TimeUnit.MINUTES)

    fun loadStarRepos(login: String) =
            object : ItemKeyedBoundResource<String, Repo, ApolloStartRepositoriesQuery.Data>(){

                private var repoResult: StarRepoResult? = null

                val order = StarOrder
                        .builder()
                        .field(StarOrderField.STARRED_AT)
                        .direction(OrderDirection.DESC).build()

                var query: ApolloStartRepositoriesQuery? = null

                override fun saveCallResult(item: ApolloStartRepositoriesQuery.Data) {
                    repoResult = saveStarRepo(query, item)
                    Timber.d("saveCallResult, pageInfo = ${repoResult?.pageInfo}")
                }

                override fun shouldFetch(data: List<Repo>?): Boolean =
                        (data == null || data.isEmpty() || reposRateLimiter.shouldFetch(query))

                override fun onFetchFailed() {
                    super.onFetchFailed()
                    reposRateLimiter.reset(query)
                }

                override fun loadFromDb(): LiveData<List<Repo>>  {
                    val repoResultLive =
                            repoDb.repoDao().loadStarRepoResult(
                                    login = login,
                                    startFirst = query?.startFirst() ?: 0,
                                    after = query?.after() ?: "")
                    return Transformations.switchMap(repoResultLive){
                        repoDb.repoDao().loadRepoOrderById(it?.repoIds ?: emptyList())
                    }
                }


                override fun hasNextPage(): Boolean {
                    return repoResult?.pageInfo?.hasNextPage ?: false
                }

                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>)
                        : LiveData<ApiResponse<ApolloStartRepositoriesQuery.Data>> {
                    val initialQuery = ApolloStartRepositoriesQuery.builder()
                            .login(login)
                            .startFirst(params.requestedLoadSize)
                            .order(order).build()
                    query = initialQuery
                    Timber.d("createInitialCall, start first = ${params.requestedLoadSize}, hasNextPage = ${repoResult?.pageInfo?.hasNextPage}")
                    val repoCall = apolloClient
                            .query(initialQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

                @SuppressLint("BinaryOperationInTimber")
                override fun createAfterCall(params: ItemKeyedDataSource.LoadParams<String>):
                        LiveData<ApiResponse<ApolloStartRepositoriesQuery.Data>> {
                    val afterQuery = ApolloStartRepositoriesQuery.builder()
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

                override fun getKey(item: Repo): String = item.id

            }.asListing( Config(
                    pageSize = 10,
                    enablePlaceholders = false,
                    initialLoadSizeHint = 10))


    private fun saveStarRepo(
            query: ApolloStartRepositoriesQuery?,
            data: ApolloStartRepositoriesQuery.Data): StarRepoResult? {
        query ?: return null
        val repoList = data.mapperToRepoVoList()
        val repoIds = repoList.map { it.id }
        val result = StarRepoResult(
                login = data.user?.login ?: "",
                repoIds = repoIds,
                startFirst = query.startFirst(),
                after = query.after() ?: "",
                pageInfo = data.getPageInfo())
        repoDb.runInTransaction {
        repoDb.repoDao().insertRepos(repoList)
        repoDb.repoDao().insertUserRepoResult(result)
        }
        return result
    }

    private fun ApolloStartRepositoriesQuery.startFirst() =
            variables().startFirst().value ?: 0

    private fun ApolloStartRepositoriesQuery.after() =
            variables().after().value
}