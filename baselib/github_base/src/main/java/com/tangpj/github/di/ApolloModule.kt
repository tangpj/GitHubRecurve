package com.tangpj.github.di

import com.apollographql.apollo.ApolloClient
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
}