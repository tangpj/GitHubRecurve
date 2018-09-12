package com.tangpj.oauth2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tangpj.github.viewmodel.GithubViewModelFactory
import com.tangpj.oauth2.ui.Oauth2ViewModel
import com.tangpj.recurve.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class Oauth2ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(Oauth2ViewModel::class)
    abstract fun bindOauth2ViewModel(oauth2ViewModel: Oauth2ViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}