package com.tangpj.oauth2.provider

import com.tangpj.oauth2.di.Oauth2Scope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TokenModule{

    @Oauth2Scope
    @ContributesAndroidInjector
    abstract fun contributesTokenProvider(): TokenProvider

}