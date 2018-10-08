package com.tangpj.github.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.tangpj.github.db.GithubDb
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AppModule{


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient
            = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
                .databaseBuilder(app, GithubDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}