package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.tangpj.github.vo.Repo
import com.tangpj.recurve.resource.Resource
import com.tangpj.recurve.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(@Inject val apolloClient: ApolloClient){

    private val repoRateLimiter = RateLimiter<Repo>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>>{

    }

}