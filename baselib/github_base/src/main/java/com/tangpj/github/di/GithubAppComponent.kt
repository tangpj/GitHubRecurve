package com.tangpj.github.di

import com.tangpj.github.GithubApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    GithubAppModule::class,
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class])

interface GithubAppComponent : AndroidInjector<GithubApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>()
}