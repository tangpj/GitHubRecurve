package com.tangpj.github.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tangpj.recurve.viewmodel.RecurevViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [OkHttpModule::class, DbModule::class])
class GithubAppModule{

    @Singleton
    @Provides
    fun bindViewModelFactory(creators: Map<Class< out ViewModel>, @JvmSuppressWildcards ViewModel>)
            : ViewModelProvider.Factory = RecurevViewModelFactory(creators)

}