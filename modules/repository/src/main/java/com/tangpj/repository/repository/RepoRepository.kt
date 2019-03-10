package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.domain.RepoFlag
import com.tangpj.repository.db.RepoDao
import com.tangpj.repository.vo.RepoVo
import com.tangpj.recurve.apollo.LiveDataApollo

import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.StartReposioriesQuery
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
         val apolloClient: ApolloClient,
         val repoDao: RepoDao){

    private val repoRateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(login: String) =
            object : NetworkBoundResource<List<RepoVo>, StartReposioriesQuery.Data>(){
                override fun saveCallResult(item: StartReposioriesQuery.Data) {
                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        data == null || data.isEmpty() || repoRateLimiter.shouldFetch(login)

                override fun loadFromDb(): LiveData<List<RepoVo>>  {
                    val repoIds = repoDao.loadUserRepoResult(login, RepoFlag.STAR)
                    return repoDao.loadRepositories(repoIds)
                }


                override fun createCall(): LiveData<ApiResponse<StartReposioriesQuery.Data>> {
                    val query = StartReposioriesQuery.builder().login(login).build()
                    val repoCall = apolloClient
                            .query(query)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

            }.asLiveData()

}