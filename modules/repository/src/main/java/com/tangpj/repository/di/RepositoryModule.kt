package com.tangpj.repository.di

import com.tangpj.recurve.recyclerview.adapter.ModulesAdapter
import com.tangpj.repository.ui.repositories.RepositoriesModule
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoriesModule::class])
class RepositoryModule{

    @Provides
    fun providesModulesAdapter() = ModulesAdapter()
}