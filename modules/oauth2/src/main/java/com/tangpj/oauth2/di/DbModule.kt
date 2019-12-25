package com.tangpj.oauth2.di

import android.content.Context
import androidx.room.Room
import com.tangpj.oauth2.db.GithubDb
import dagger.Module
import dagger.Provides

@Module
class DbModule{

    @Oauth2Scope
    @Provides
    fun provideDb(context: Context): GithubDb =
            Room.databaseBuilder(context, GithubDb::class.java, "github.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    fun providerGithubTokenDao(githubDb: GithubDb): com.tangpj.oauth2.db.GithubTokenDao =
            githubDb.tokenDao()




}