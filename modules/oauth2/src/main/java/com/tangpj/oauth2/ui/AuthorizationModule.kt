package com.tangpj.oauth2.ui

import androidx.lifecycle.ViewModel
import com.recurve.dagger2.ViewModelKey
import com.tangpj.oauth2.di.Oauth2Scope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AuthorizationModule{

    @ContributesAndroidInjector
    abstract fun contributeAuthorizationActivity(): AuthorizationActivity

    @ContributesAndroidInjector
    abstract fun contributesAuthorizationFragment(): AuthorizationFragment

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    abstract fun bindAtuhorizationViewModel(authorizationViewModel: AuthorizationViewModel): ViewModel
}