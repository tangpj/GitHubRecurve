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
import com.tangpj.repository.BlobDetailQuery
import com.tangpj.repository.FileTreeQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.mapper.getFileContent
import com.tangpj.repository.mapper.getFileItems
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.valueObject.query.getApolloBlobQuery
import com.tangpj.repository.valueObject.query.getApolloFileTreeQuery
import com.tangpj.repository.valueObject.result.FileContentResult
import com.tangpj.repository.valueObject.result.FileItemsResult
import com.tangpj.repository.vo.FileContent
import com.tangpj.repository.vo.FileItem
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FileRepository @Inject constructor(
        val apolloClient: ApolloClient,
        val repoDb: RepositoryDb){


    private val fileTreeRateLimiter = RateLimiter<GitObjectQuery>(5, TimeUnit.MINUTES)

    fun loadFileDirectory(gitObjectQuery: GitObjectQuery) =
            object : NetworkBoundResource<List<FileItem>, FileTreeQuery.Data>(){
                override fun saveCallResult(item: FileTreeQuery.Data) {
                    val fileItems = item.getFileItems()
                    val fileItemsResult = FileItemsResult(
                            owner = gitObjectQuery.repoDetailQuery.owner,
                            repoName = gitObjectQuery.repoDetailQuery.name,
                            expression = gitObjectQuery.getExpression(),
                            itemIds = fileItems.map { it.id })
                    repoDb.runInTransaction {
                        repoDb.repoDetailDao().inserFileItems(fileItems)
                        repoDb.repoDetailDao().insertFileItemResult(fileItemsResult)
                    }
                }

                override fun shouldFetch(data: List<FileItem>?): Boolean =
                        data == null || fileTreeRateLimiter.shouldFetch(gitObjectQuery)

                override fun loadFromDb(): LiveData<List<FileItem>> = Transformations.switchMap(repoDb.repoDetailDao().loadFileItemsResult(
                        gitObjectQuery.repoDetailQuery.owner,
                        gitObjectQuery.repoDetailQuery.name,
                        gitObjectQuery.getExpression())){
                    if (it == null){
                        AbsentLiveData.create()
                    }else{
                        repoDb.repoDetailDao().loadFileItemsById(it.itemIds)
                    }

                }

                override fun createCall(): LiveData<ApiResponse<FileTreeQuery.Data>> {
                    val fileTreeQuery = gitObjectQuery.getApolloFileTreeQuery()
                    val fileTreeCall = apolloClient.query(fileTreeQuery)
                    return LiveDataApollo.from(fileTreeCall)

                }

            }.asLiveData()

    fun loadFileContent(gitObjectQuery: GitObjectQuery) =
            object : NetworkBoundResource<FileContent, BlobDetailQuery.Data>(){

                override fun saveCallResult(item: BlobDetailQuery.Data) {
                    val fileContent = item.getFileContent(gitObjectQuery.getExpression())
                    fileContent ?: return
                    val fileContentResult = FileContentResult(
                            owner = gitObjectQuery.repoDetailQuery.owner,
                            repoName = gitObjectQuery.repoDetailQuery.name,
                            expression = gitObjectQuery.getExpression(),
                            fileContentId = fileContent.id)
                    repoDb.runInTransaction {
                        repoDb.repoDetailDao().insertFileContent(fileContent)
                        repoDb.repoDetailDao().insertFileContentResult(fileContentResult)
                    }
                }

                override fun shouldFetch(data: FileContent?): Boolean =
                        data == null || fileTreeRateLimiter.shouldFetch(gitObjectQuery)

                override fun loadFromDb(): LiveData<FileContent> =  Transformations.switchMap(

                        repoDb.repoDetailDao().loadFileContentResult(
                                gitObjectQuery.repoDetailQuery.owner,
                                gitObjectQuery.repoDetailQuery.name,
                                gitObjectQuery.getExpression())){ fileContentResult ->
                    if (fileContentResult == null){
                        AbsentLiveData.create()
                    }else{
                        repoDb.repoDetailDao().loadFileContentById(fileContentResult.fileContentId)
                    }
                }

                override fun createCall(): LiveData<ApiResponse<BlobDetailQuery.Data>> {
                    val blobDetailQuery = gitObjectQuery.getApolloBlobQuery()

                    val blobCall = apolloClient.query(blobDetailQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(blobCall)


                }

            }.asLiveData()

}
