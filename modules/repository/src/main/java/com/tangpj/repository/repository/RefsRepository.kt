package com.tangpj.repository.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.tangpj.github.di.PagingConfig
import com.recurve.paging.ItemKeyedBoundResource
import com.recurve.apollo.LiveDataApollo
import com.recurve.core.resource.ApiResponse
import com.recurve.core.util.RateLimiter
import com.tangpj.repository.ApolloRefsQuery
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.entity.domain.Ref
import com.tangpj.repository.mapper.getLocalPageInfo
import com.tangpj.repository.mapper.getRefs
import com.tangpj.repository.valueObject.query.Prefix
import com.tangpj.repository.valueObject.query.RefsQuery
import com.tangpj.repository.valueObject.query.getApolloRefsQuery
import com.tangpj.repository.valueObject.result.RefsResult
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefsRepository @Inject constructor(
        private val apolloClient: ApolloClient,
        private val repoDb: RepositoryDb,
        private val pagingConfig: PagingConfig){

    private val refsRateLimiter = RateLimiter<RefsQuery>(1, TimeUnit.MINUTES)


    fun loadRefs(refsQuery: RefsQuery) =
            object : ItemKeyedBoundResource<String, Ref, ApolloRefsQuery.Data>(){

                private var query: ApolloRefsQuery? = null
                private var refsResult: RefsResult? = null

                override fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<String>): LiveData<ApiResponse<ApolloRefsQuery.Data>> {
                    val initialQuery =
                            refsQuery.getApolloRefsQuery(params.requestedLoadSize)
                    query = initialQuery
                    Timber.d("""createInitialCall, start first = ${params.requestedLoadSize},
                        |hasNextPage = ${refsResult?.pageInfo?.hasNextPage}""".trimMargin())
                    val refsCall = apolloClient.query(initialQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(refsCall)
                }

                override fun createAfterCall(params: ItemKeyedDataSource.LoadParams<String>): LiveData<ApiResponse<ApolloRefsQuery.Data>>? {
                    val afterQuery = refsQuery.getApolloRefsQuery(
                            startFirst = params.requestedLoadSize,
                            after = refsResult?.pageInfo?.endCursor
                    )
                    query = afterQuery
                    val refsCall = apolloClient
                            .query(afterQuery)
                            .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    return LiveDataApollo.from(refsCall)
                }

                override fun getKey(item: Ref): String = item.id

                override fun saveCallResult(item: ApolloRefsQuery.Data) {
                    query?.let {
                        refsResult = saveRefs(it, item)
                    }
                }

                override fun shouldFetch(data: List<Ref>?): Boolean  =
                        (data == null || data.isEmpty() || refsRateLimiter.shouldFetch(refsQuery))


                override fun loadFromDb(): LiveData<List<Ref>> {
                    val refsResultLive = repoDb.refDao().loadRefsResult(
                            login = refsQuery.repoDetailQuery.login,
                            repoName = refsQuery.repoDetailQuery.name,
                            prefix = refsQuery.prefix.refName,
                            startFirst = query?.variables()?.startFirst()?.value ?: pagingConfig.initialLoadSizeHint,
                            after = query?.variables()?.after()?.value ?: ""
                    )
                    return Transformations.switchMap(refsResultLive){
                        refsResult =it
                        repoDb.refDao().loadRefsOrderById(it?.refsIds ?: emptyList())
                    }
                }

                override fun hasNextPage(): Boolean  =
                        refsResult?.pageInfo?.hasNextPage == true

            }.asListing(pagingConfig.getConfig())

    private fun saveRefs(
            query: ApolloRefsQuery,
            data: ApolloRefsQuery.Data) : RefsResult? {
        val refsDto = data.repository?.refsDto
        refsDto ?: return null
        val refs = refsDto.getRefs()
        val pageInfo = refsDto.getLocalPageInfo()
        val result = RefsResult(
                login = query.variables().login(),
                repoName = query.variables().name(),
                prefix = Prefix.getPrefixRefName(query.variables().refPrefix()),
                refsIds = refs.map { it.id },
                startFirst = query.variables().startFirst().value ?: 10,
                after =  query.variables().after().value ?: "",
                pageInfo = pageInfo
        )
        repoDb.runInTransaction {
            repoDb.refDao().insertRefs(refs)
            repoDb.refDao().insertRefsResult(result)
        }
        return result
    }

}