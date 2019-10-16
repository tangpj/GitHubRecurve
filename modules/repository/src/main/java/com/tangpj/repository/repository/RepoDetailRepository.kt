package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.recurve.apollo.LiveDataApollo
import com.recurve.core.resource.ApiResponse
import com.recurve.core.resource.NetworkBoundResource
import com.recurve.core.util.RateLimiter
import com.tangpj.repository.ApolloRepoDetailQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.mapper.getRepoDetail
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.repository.valueObject.query.getApolloRepoDetailQuery
import com.tangpj.repository.entity.domain.repo.RepoDetail
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){

    private val reposDetailRateLimiter = RateLimiter<RepoDetailQuery>(5, TimeUnit.MINUTES)

    fun loadRepoDetail(repoDetailQuery: RepoDetailQuery) =
            object : NetworkBoundResource<RepoDetail, ApolloRepoDetailQuery.Data>(){
                override fun saveCallResult(item: ApolloRepoDetailQuery.Data) {
                    val repoDetail = item.getRepoDetail()
                    repoDetail ?: return
                    repoDb.repoDetailDao().insertRepoDetail(repoDetail)

                }

                override fun shouldFetch(data: RepoDetail?): Boolean =
                        data == null || reposDetailRateLimiter.shouldFetch(repoDetailQuery)

                override fun loadFromDb(): LiveData<RepoDetail> =
                        repoDb.repoDetailDao().loadRepoDetail(
                                login = repoDetailQuery.login,
                                name = repoDetailQuery.name)

                override fun createCall(): LiveData<ApiResponse<ApolloRepoDetailQuery.Data>> {
                    val repoDetailCall =
                            apolloClient.query( repoDetailQuery.getApolloRepoDetailQuery())
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoDetailCall)
                }

                override fun onFetchFailed() {
                    super.onFetchFailed()
                    reposDetailRateLimiter.reset(repoDetailQuery)
                }
            }.asLiveData()
}