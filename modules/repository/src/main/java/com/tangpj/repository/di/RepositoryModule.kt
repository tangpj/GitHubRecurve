package com.tangpj.repository.di

import androidx.room.Room
import com.tangpj.github.GithubApp
import com.tangpj.recurve.recyclerview.adapter.ModulesAdapter
import com.tangpj.repository.db.RepoDao
import com.tangpj.repository.db.RepositoryDb
import com.tangpj.repository.ui.repositories.RepoModule
import dagger.Module
import dagger.Provides

@Module(includes = [RepoModule::class])
class RepositoryModule{

    @Provides
    fun providerRepositoryDb(app: GithubApp) =
            Room.databaseBuilder(app, RepositoryDb::class.java, "repository.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    fun providerRepoDao(repositoryDb: RepositoryDb): RepoDao =
            repositoryDb.repoDao()

    @Provides
    fun providesModulesAdapter() = ModulesAdapter()

}