package com.tangpj.repository.repository

import com.apollographql.apollo.ApolloClient
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.db.RepositoryDb
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){


}