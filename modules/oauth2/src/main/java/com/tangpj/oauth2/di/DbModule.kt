package com.tangpj.oauth2.di

import android.app.Application
import androidx.room.Room
import com.tangpj.github.GithubApp
import com.tangpj.oauth2.db.GithubDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule{

    @Singleton
    @Provides
    fun provideDb(app: GithubApp): GithubDb =
            Room.databaseBuilder(app, GithubDb::class.java, "github.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    fun providerGithubTokenDao(githubDb: GithubDb): com.tangpj.oauth2.db.GithubTokenDao =
            githubDb.tokenDao()




}