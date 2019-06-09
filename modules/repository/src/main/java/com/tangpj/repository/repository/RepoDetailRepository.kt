package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.recurve.apollo.LiveDataApollo
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.BlodDetailQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.vo.RepoFileContent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){

    private val blodDetailRateLimiter = RateLimiter<BlodDetailQuery>(5, TimeUnit.MINUTES)

    fun loadFileContent(owner: String, name: String, expression: String) =
            object : NetworkBoundResource<RepoFileContent, BlodDetailQuery.Data>(){

                var preQuery: BlodDetailQuery? = null

                override fun saveCallResult(item: BlodDetailQuery.Data) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun shouldFetch(data: RepoFileContent?): Boolean =
                        data == null || blodDetailRateLimiter.shouldFetch(preQuery)

                override fun loadFromDb(): LiveData<RepoFileContent> =
                        repoDb.repoDeatilDao().loadFileContentByRepo(owner, name, expression)

                override fun createCall(): LiveData<ApiResponse<BlodDetailQuery.Data>> {
                    val blodDetailQuery = BlodDetailQuery.builder()
                            .owner(owner)
                            .name(name)
                            .expression(expression).build()
                    preQuery = blodDetailQuery
                    val blodCall = apolloClient.query(blodDetailQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(blodCall)


                }

            }

}
