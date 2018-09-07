package com.tangpj.oauth2.di

import com.tangpj.oauth2.ui.Oauth2ViewModel
import com.tangpj.recurve.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class Oauth2ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(Oauth2ViewModel::class)
    abstract fun bindOauth2ViewModel(): Oauth2ViewModel
}