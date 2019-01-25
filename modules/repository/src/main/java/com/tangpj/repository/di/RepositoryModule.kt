package com.tangpj.repository.di

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tangpj.recurve.recyclerview.adapter.ModulesAdapter
import com.tangpj.repository.R
import com.tangpj.repository.ui.repositories.RepositoriesActivity
import com.tangpj.repository.ui.repositories.RepositoriesModule
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoriesModule::class])
class RepositoryModule{

    @Provides
    fun providesModulesAdapter() = ModulesAdapter()
}