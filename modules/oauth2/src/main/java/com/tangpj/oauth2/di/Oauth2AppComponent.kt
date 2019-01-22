package com.tangpj.oauth2.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.GithubAppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    GithubAppModule::class,
    Oauth2ComponentModule::class
])
interface Oauth2AppComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>(){
    }
}