package com.tangpj.github.di

import androidx.room.Room
import com.tangpj.github.GithubApp
import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.db.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule{

    @Singleton
    @Provides
    fun provideDb(app: GithubApp): GithubDb {
        return Room
                .databaseBuilder(app, GithubDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    fun providerGithubTokenDao(githubDb: GithubDb): GithubTokenDao =
            githubDb.tokenDao()

    @Provides
    fun providerRepoDao(githubDb: GithubDb): RepoDao =
            githubDb.repoDao()


}