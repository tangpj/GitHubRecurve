package com.tangpj.repository.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.ApolloModule
import com.tangpj.github.di.GithubAppComponent
import com.tangpj.github.di.GithubAppModule
import com.tangpj.github.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@RepositoryScope
@Component(modules = [RepositoryModule::class,
    ActivityModule::class],
        dependencies = [GithubAppComponent::class])

interface RepositoryComponent : AndroidInjector<GithubApp>{


    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>(){
        abstract fun githubAppComponent(githubAppComponent: GithubAppComponent)

    }
}