package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.tangpj.github.di.PagingConfig
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.entry.vo.commit.Commit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CommitRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb,
        val pagingConfig: PagingConfig){


    private val commitRateLimiter = RateLimiter<ApolloCommitsQuery>(1, TimeUnit.MINUTES)

    fun loadCommits() =
            object : ItemKeyedBoundResource<String, Commit, ApolloCommitsQuery.Data>(){
                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>): LiveData<ApiResponse<ApolloCommitsQuery.Data>> {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun getKey(item: Commit): String {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun saveCallResult(item: ApolloCommitsQuery.Data) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun shouldFetch(data: List<Commit>?): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun loadFromDb(): LiveData<List<Commit>> {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }

}