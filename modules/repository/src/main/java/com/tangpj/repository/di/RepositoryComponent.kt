package com.tangpj.repository.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.ApolloModule
import com.tangpj.github.di.GithubAppModule
import dagger.Component
import dagger.android.AndroidInjector

@RepositoryScope
@Component(modules = [
    GithubAppModule::class,
    RepositoryModule::class,
    ApolloModule::class,
    ActivityModule::class])

interface RepositoryComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>()
}