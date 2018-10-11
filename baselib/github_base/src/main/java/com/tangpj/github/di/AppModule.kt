package com.tangpj.github.di

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.tangpj.github.GithubApp
import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: Interceptor): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()


    @Singleton
    @Provides
    fun providerTokenInterceptor(token: LiveData<GithubToken>) =
            Interceptor {
                val original: Request = it.request()
                val requestBuilder = original.newBuilder()
                token.value?.let { token ->
                    requestBuilder.addHeader("Authorization","token ${token.accessToken}")
                }
                it.proceed(requestBuilder.build())
            }

    @Singleton
    @Provides
    fun provideDb(app: GithubApp): GithubDb {
        return Room
                .databaseBuilder(app, GithubDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun providerGithubTokenDao(githubDb: GithubDb): GithubTokenDao =
            githubDb.tokenDao()


}