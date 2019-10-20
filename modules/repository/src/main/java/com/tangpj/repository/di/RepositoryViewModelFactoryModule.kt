package com.tangpj.repository.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.recurve.core.viewmodel.RecurevViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryViewModelFactoryModule{

    @RepositoryScope
    @Provides
    fun bindViewModelFactory(creators: Map<Class< out ViewModel>, @JvmSuppressWildcards ViewModel>)
            : ViewModelProvider.Factory = RecurevViewModelFactory(creators)
}