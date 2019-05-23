package com.tangpj.github.di

import com.apollographql.apollo.ApolloClient
import com.tangpj.github.BuildConfig
import com.tangpj.github.core.apollo.DateCustomerAdapter
import com.tangpj.github.type.CustomType
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApolloModule{

    @Provides
    fun provideApolloClient(
            okHttpClient: OkHttpClient): ApolloClient.Builder =
            ApolloClient.builder().subscriptionHeartbeatTimeout(3, TimeUnit.SECONDS).okHttpClient(okHttpClient)
}