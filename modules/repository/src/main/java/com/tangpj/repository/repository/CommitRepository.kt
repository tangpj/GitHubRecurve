package com.tangpj.repository.repository

import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.tangpj.github.di.PagingConfig
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.db.RepositoryDb
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CommitRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb,
        val pagingConfig: PagingConfig){


    private val commitRateLimiter = RateLimiter<ApolloCommitsQuery>(1, TimeUnit.MINUTES)


}