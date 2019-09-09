package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.di.PagingConfig
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.recurve.apollo.LiveDataApollo
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.entity.author.CommitAuthor
import com.tangpj.repository.entity.commit.Commit
import com.tangpj.repository.mapper.getApolloAuthor
import com.tangpj.repository.valueObject.result.CommitsResult
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CommitRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb,
        val pagingConfig: PagingConfig){


    private val commitRateLimiter = RateLimiter<ApolloCommitsQuery>(1, TimeUnit.MINUTES)

    fun loadCommits(login: String, name: String, author: CommitAuthor? = null ) =
            object : ItemKeyedBoundResource<String, Commit, ApolloCommitsQuery.Data>(){

                private var commitsResult: CommitsResult? = null
                private lateinit var query: ApolloCommitsQuery

                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>): LiveData<ApiResponse<ApolloCommitsQuery.Data>> {
                    val initialQuery = ApolloCommitsQuery.builder()
                            .login(login)
                            .name(name)
                            .author(author?.getApolloAuthor())
                            .startFirst(params.requestedLoadSize)
                            .build()
                    query = initialQuery
                    Timber.d("""createInitialCall, start first = ${params.requestedLoadSize},
                        |hasNextPage = ${commitsResult?.pageInfo?.hasNextPage}""".trimMargin())
                    val commitCall = apolloClient.query(initialQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(commitCall)

                }

                override fun createAfterCall(params: ItemKeyedDataSource.LoadParams<String>): LiveData<ApiResponse<ApolloCommitsQuery.Data>>? {
                    val afterQuery = ApolloCommitsQuery.builder()
                            .login(login)
                            .name(name)
                            .author(author?.getApolloAuthor())
                            .startFirst(params.requestedLoadSize)
                            .after(commitsResult?.after)
                            .build()
                    query = afterQuery
                    val commitCall = apolloClient
                            .query(afterQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(commitCall)
                }

                override fun getKey(item: Commit): String = item.id


                override fun saveCallResult(item: ApolloCommitsQuery.Data) {

                }

                override fun shouldFetch(data: List<Commit>?): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun loadFromDb(): LiveData<List<Commit>> {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }




}