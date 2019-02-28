package com.tangpj.github.di

import androidx.room.Room
import com.tangpj.github.GithubApp
import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
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
    fun providerGithubTokenDao(githubDb: GithubDb): GithubTokenDao =
            githubDb.tokenDao()




}