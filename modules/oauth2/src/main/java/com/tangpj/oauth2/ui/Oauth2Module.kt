package com.tangpj.oauth2.ui

import androidx.lifecycle.ViewModel
import com.tangpj.oauth2.ui.Oauth2Activity
import com.tangpj.oauth2.ui.Oauth2Fragment
import com.tangpj.oauth2.ui.Oauth2ViewModel
import com.tangpj.recurve.dagger2.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class Oauth2Module{

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): Oauth2Activity

    @ContributesAndroidInjector
    abstract fun contributesOauth2Fragment(): Oauth2Fragment

    @Binds
    @IntoMap
    @ViewModelKey(Oauth2ViewModel::class)
    abstract fun bindOauth2ViewModel(oauth2ViewModel: Oauth2ViewModel): ViewModel
}