package com.tangpj.github.di

import android.app.Application
import androidx.room.Room
import com.tangpj.github.db.GithubDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GithubDbModule{

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
                .databaseBuilder(app, GithubDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }

}