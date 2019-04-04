package com.tangpj.oauth2.di

import androidx.room.Room
import com.tangpj.github.GithubApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule{

    @Singleton
    @Provides
    fun provideDb(app: GithubApp): com.tangpj.oauth2.db.GithubDb =
            Room.databaseBuilder(app, com.tangpj.oauth2.db.GithubDb::class.java, "github.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    fun providerGithubTokenDao(githubDb: com.tangpj.oauth2.db.GithubDb): com.tangpj.oauth2.db.GithubTokenDao =
            githubDb.tokenDao()




}