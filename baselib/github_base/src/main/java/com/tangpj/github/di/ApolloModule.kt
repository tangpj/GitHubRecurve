package com.tangpj.github.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
class ApolloModule{

    @Provides
    fun provideApolloClient(
            okHttpClient: OkHttpClient): ApolloClient.Builder =
            ApolloClient.builder().subscriptionHeartbeatTimeout(3, TimeUnit.SECONDS).okHttpClient(okHttpClient)
}