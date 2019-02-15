package com.tangpj.repository.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.ApolloModule
import com.tangpj.github.di.GithubAppModule
import com.tangpj.github.di.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    GithubAppModule::class,
    ViewModelFactoryModule::class,
    ApolloModule::class,
    RepositoryModule::class])

interface RepositoryComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>()
}