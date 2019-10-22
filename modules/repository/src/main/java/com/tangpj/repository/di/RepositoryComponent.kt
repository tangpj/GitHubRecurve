package com.tangpj.repository.di

import android.content.Context
import com.tangpj.github.GithubApp
import com.tangpj.github.di.ApolloModule
import com.tangpj.github.di.GithubAppServerModule
import com.tangpj.github.di.GithubComponent
import dagger.Component
import dagger.android.AndroidInjector

@RepositoryScope
@Component(modules = [
    GithubAppServerModule::class,
    RepositoryModule::class,
    RepositoryViewModelFactoryModule::class,
    ApolloModule::class,
    ActivityModule::class])

interface RepositoryComponent : AndroidInjector<GithubApp>{
    fun githubComponent() : GithubComponent.Factory

}