package com.tangpj.repository.repository

import com.apollographql.apollo.ApolloClient
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.ApolloRepoDetailQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){

    private val reposDetailRateLimiter = RateLimiter<ApolloRepoDetailQuery>(5, TimeUnit.MINUTES)

//    fun loadRepoDetail(repoDetailQuery: RepoDetailQuery) =
//            object : NetworkBoundResource<>
}