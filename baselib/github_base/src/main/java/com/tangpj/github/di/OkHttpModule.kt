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
            val tokens = tokenDao.loadToken()
            val original: Request = it.request()
            val requestBuilder = original.newBuilder()
            tokens.value?.let { _token ->
                if (_token.isNotEmpty()){
                    requestBuilder.addHeader("Authorization","token ${_token[0].accessToken}")
                }
            }
            it.proceed(requestBuilder.build())
        }
    }

}