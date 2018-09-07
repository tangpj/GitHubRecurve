package com.tangpj.oauth2.di

import com.tangpj.oauth2.ui.Oauth2Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class Oauth2FragmentModule{

    @ContributesAndroidInjector
    abstract fun contributesOauth2Fragment(): Oauth2Fragment

}