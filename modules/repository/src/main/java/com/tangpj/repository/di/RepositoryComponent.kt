package com.tangpj.repository.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.ApolloModule
import com.tangpj.github.di.GithubComponent
import com.tangpj.github.di.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@RepositoryScope
@Component(modules = [
    RepositoryModule::class,
    AndroidSupportInjectionModule::class,
    RepositoryViewModelFactoryModule::class,
    ApolloModule::class,
    ActivityModule::class], dependencies = [GithubComponent::class])

interface RepositoryComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>(){
        abstract fun setGithubComponent(githubComponent: GithubComponent): Builder
    }
}