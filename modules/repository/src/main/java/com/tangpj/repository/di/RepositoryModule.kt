package com.tangpj.repository.di

import com.tangpj.recurve.recyclerview.adapter.ModulesAdapter
import com.tangpj.repository.ui.repositories.RepositoriesModule
import com.tangpj.repository.ui.repositories.RepositoryCreator
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoriesModule::class])
class RepositoryModule{

    @RepositoryScope
    @Provides
    fun providesRepositoryCreator(adapter: ModulesAdapter): RepositoryCreator =
            RepositoryCreator(adapter)
}