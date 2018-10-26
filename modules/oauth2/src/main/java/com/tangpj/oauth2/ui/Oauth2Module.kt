package com.tangpj.oauth2.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class Oauth2Module{

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): Oauth2Activity

    @ContributesAndroidInjector
    abstract fun contributesOauth2Fragment(): Oauth2Fragment
}