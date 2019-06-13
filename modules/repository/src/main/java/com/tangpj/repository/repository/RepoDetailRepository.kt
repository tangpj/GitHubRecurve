package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.utils.AbsentLiveData
import com.tangpj.recurve.apollo.LiveDataApollo
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.BlodDetailQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.vo.FileContent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){

    private val blodDetailRateLimiter = RateLimiter<BlodDetailQuery>(5, TimeUnit.MINUTES)

    fun loadFileContent(owner: String, name: String, expression: String) =
            object : NetworkBoundResource<FileContent, BlodDetailQuery.Data>(){

                var preQuery: BlodDetailQuery? = null

                override fun saveCallResult(item: BlodDetailQuery.Data) {

                }

                override fun shouldFetch(data: FileContent?): Boolean =
                        data == null || blodDetailRateLimiter.shouldFetch(preQuery)

                override fun loadFromDb(): LiveData<FileContent> =  Transformations.switchMap(
                        repoDb.repoDeatilDao().loadFileContentResult(owner, name, expression)){ fileContent ->
                    if (fileContent == null){
                        AbsentLiveData.create()
                    }else{
                        repoDb.repoDeatilDao().loadFileContentById(fileContent.fileContentId)
                    }
                }

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
