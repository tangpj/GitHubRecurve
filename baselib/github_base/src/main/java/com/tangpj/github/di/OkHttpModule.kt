package com.tangpj.github.di

import com.tangpj.github.db.GithubTokenDao
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
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
            val token = tokenDao.loadTokenForIO()
            val original: Request = it.request()
            val requestBuilder = original.newBuilder()
            token?.let {
                Timber.d("set authorization header")
                requestBuilder.addHeader("Authorization","token ${it.accessToken}")
            }

            it.proceed(requestBuilder.build())
        }
    }

}