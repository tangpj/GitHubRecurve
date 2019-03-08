package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.utils.Mapping
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
         val repoDao: RepoDao,
         val copier: Mapping){

    private val repoRateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(login: String) =
            object : NetworkBoundResource<List<RepoVo>, StartReposioriesQuery.Data>(){
                override fun saveCallResult(item: StartReposioriesQuery.Data) {
                    item.user()?.starredRepositories()?.edges()?.map {
                        Timber.d(it.node().fragments().repo().marshaller().toString())
                    }

                }

                override fun shouldFetch(data: List<RepoVo>?): Boolean =
                        data == null || data.isEmpty() || repoRateLimiter.shouldFetch(login)

                override fun loadFromDb(): LiveData<List<RepoVo>>  =
                    repoDao.loadRepositories(login)


                override fun createCall(): LiveData<ApiResponse<StartReposioriesQuery.Data>> {
                    val query = StartReposioriesQuery.builder().login(login).build()
                    val repoCall = apolloClient
                            .query(query)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(repoCall)
                }

            }.asLiveData()

}