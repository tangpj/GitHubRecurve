package com.tangpj.oauth2.di

import com.tangpj.oauth2.ui.Oauth2Activity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class Oauth2AcitivtyModule{

    @ContributesAndroidInjector(modules = [Oauth2FragmentModule::class])
    abstract fun contributeMainActivity(): Oauth2Activity
}