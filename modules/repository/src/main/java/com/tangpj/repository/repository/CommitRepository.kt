package com.tangpj.repository.repository

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.di.PagingConfig
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.recurve.apollo.LiveDataApollo
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.util.RateLimiter
import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.entity.author.CommitAuthor
import com.tangpj.repository.entity.commit.Commit
import com.tangpj.repository.mapper.getApolloAuthor
import com.tangpj.repository.mapper.mapperToPageInfoCommitsPair
import com.tangpj.repository.valueObject.result.CommitsResult
import com.tangpj.repository.vo.CommitVo
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CommitRepository @Inject constructor(
        private val apolloClient: ApolloClient,
        private val repoDb: RepositoryDb,
        private val pagingConfig: PagingConfig){


    private val commitRateLimiter = RateLimiter<ApolloCommitsQuery>(1, TimeUnit.MINUTES)

    fun loadCommits(login: String, repoName: String, author: CommitAuthor? = null ) =
            object : ItemKeyedBoundResource<String, CommitVo, ApolloCommitsQuery.Data>(){

                private var commitsResult: CommitsResult? = null
                private lateinit var query: ApolloCommitsQuery

                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>): LiveData<ApiResponse<ApolloCommitsQuery.Data>> {
                    val initialQuery = ApolloCommitsQuery.builder()
                            .login(login)
                            .repoName(repoName)
                            .author(author?.getApolloAuthor())
                            .startFirst(params.requestedLoadSize)
                            .build()
                    query = initialQuery
                    Timber.d("""createInitialCall, start first = ${params.requestedLoadSize},
                        |hasNextPage = ${commitsResult?.pageInfo?.hasNextPage}""".trimMargin())
                    val commitCall = apolloClient.query(initialQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(commitCall)

                }

                override fun createAfterCall(params: ItemKeyedDataSource.LoadParams<String>): LiveData<ApiResponse<ApolloCommitsQuery.Data>>? {
                    val afterQuery = ApolloCommitsQuery.builder()
                            .login(login)
                            .repoName(repoName)
                            .author(author?.getApolloAuthor())
                            .startFirst(params.requestedLoadSize)
                            .after(commitsResult?.after)
                            .build()
                    query = afterQuery
                    val commitCall = apolloClient
                            .query(afterQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(commitCall)
                }

                override fun getKey(item: CommitVo): String = item.commit.id


                override fun saveCallResult(item: ApolloCommitsQuery.Data) {
                    commitsResult = saveCommits(query, item)
                }

                override fun shouldFetch(data: List<CommitVo>?): Boolean =
                        (data == null || data.isEmpty() || commitRateLimiter.shouldFetch(query))

                override fun loadFromDb(): LiveData<List<CommitVo>> {
                    val commitResultLiveData = repoDb.commitDao().loadCommitResult(
                            login = login,
                            repoName = repoName,
                            authorId = author?.id)

                    val commitsLiveData = Transformations.switchMap(commitResultLiveData) {
                        repoDb.commitDao().loadCommitsOrderById(it.commitIds)
                    }
                    return Transformations.map(commitsLiveData){ commits ->
                        commits.map { CommitVo(commit = it, ) }
                    }

                }

            }


    private fun saveCommits(
            query: ApolloCommitsQuery,
            data: ApolloCommitsQuery.Data
    ): CommitsResult {

        val pageInfoCommitPair = data.mapperToPageInfoCommitsPair()
        val pageInfo = pageInfoCommitPair.first
        val commits = pageInfoCommitPair.second
        val commitIds = commits.map { it.id }
        val result = CommitsResult(
                login = query.variables().login().value ?: "",
                repoName = query.variables().repoName().value ?: "",
                authorId = query.variables().author().value?.id() ?: "",
                commitIds = commitIds,
                startFirst = query.variables().startFirst().value ?: 10,
                after = query.variables().after().value ?: "",
                pageInfo = pageInfo
        )
        repoDb.runInTransaction {
            repoDb.commitDao().insertCommits(commits)
            repoDb.commitDao().insertCommitsResult(result)
        }
        return result
    }



}