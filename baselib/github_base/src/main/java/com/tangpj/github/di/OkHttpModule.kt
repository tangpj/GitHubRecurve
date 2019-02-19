package com.tangpj.github.di

import com.tangpj.github.db.GithubTokenDao
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
class OkHttpModule{

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: Interceptor): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()


    @Singleton
    @Provides
    fun providerTokenInterceptor(tokenDao: GithubTokenDao): Interceptor{
        return Interceptor {
            val token = tokenDao.loadToken()
            val original: Request = it.request()
            val requestBuilder = original.newBuilder()
            token.value?.let { _token ->
                requestBuilder.addHeader("Authorization","token ${_token.accessToken}")
            }
            it.proceed(requestBuilder.build())
        }
    }

}