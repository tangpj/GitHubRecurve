package com.tangpj.github.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCache
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.tangpj.github.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class ApolloModule{

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient =
            ApolloClient
                    .builder()
                    .okHttpClient(okHttpClient)
                    .serverUrl(BuildConfig.BASE_URL)
                    .build()

    @Singleton @Provides fun provideCacheFactory(): NormalizedCacheFactory<LruNormalizedCache> =
             LruNormalizedCacheFactory(EvictionPolicy.NO_EVICTION)
                    .chain(SqlNormalizedCacheFactory(apolloSqlHelper))


}