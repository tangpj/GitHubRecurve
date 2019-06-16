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
import com.tangpj.repository.mapper.getFileContent
import com.tangpj.repository.valueObject.query.FileContentQuery
import com.tangpj.repository.valueObject.result.FileContentResult
import com.tangpj.repository.vo.FileContent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){

    private val blodDetailRateLimiter = RateLimiter<FileContentQuery>(5, TimeUnit.MINUTES)

    fun loadFileContent(fileContentQuery: FileContentQuery) =
            object : NetworkBoundResource<FileContent, BlodDetailQuery.Data>(){

                override fun saveCallResult(item: BlodDetailQuery.Data) {
                    val fileContent = item.getFileContent()
                    fileContent ?: return
                    val fileContentResult = FileContentResult(
                            owner = fileContentQuery.owner,
                            repoName = fileContentQuery.name,
                            expression = fileContentQuery.expression,
                            fileContentId = fileContent.id)
                    repoDb.runInTransaction {
                        repoDb.repoDetailDao().insertFileContent(fileContent)
                        repoDb.repoDetailDao().insertFileContentResult(fileContentResult)
                    }
                }

                override fun shouldFetch(data: FileContent?): Boolean =
                        data == null || blodDetailRateLimiter.shouldFetch(fileContentQuery)

                override fun loadFromDb(): LiveData<FileContent> =  Transformations.switchMap(

                        repoDb.repoDetailDao().loadFileContentResult(fileContentQuery)){ fileContent ->
                    if (fileContent == null){
                        AbsentLiveData.create()
                    }else{
                        repoDb.repoDetailDao().loadFileContentById(fileContent.fileContentId)
                    }
                }

                override fun createCall(): LiveData<ApiResponse<BlodDetailQuery.Data>> {
                    val blodDetailQuery = BlodDetailQuery.builder()
                            .owner(owner)
                            .name(name)
                            .expression(expression).build()
                    val blodCall = apolloClient.query(blodDetailQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(blodCall)


                }

            }

}
