package com.tangpj.github.di

import com.apollographql.apollo.ApolloClient
import com.tangpj.github.BuildConfig
import com.tangpj.github.core.apollo.DateCustomerAdapter
import com.tangpj.github.type.CustomType
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class ApolloModule{

    @Singleton
    @Provides
    fun provideApolloClient(
            okHttpClient: OkHttpClient,
            dateCustomerAdapter: DateCustomerAdapter): ApolloClient =

            ApolloClient
                    .builder()
                    .okHttpClient(okHttpClient)
                    .serverUrl(BuildConfig.BASE_URL)
                    .build()
}