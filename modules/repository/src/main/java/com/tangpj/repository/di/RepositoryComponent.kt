package com.tangpj.repository.di

import android.content.Context
import android.os.Build
import com.tangpj.github.di.*
import com.tangpj.repository.RepositoryApp
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@RepositoryScope
@Component(modules = [
    RepositoryHelperModule::class,
    AndroidSupportInjectionModule::class,
    RepositoryViewModelFactoryModule::class,
    ApolloModule::class,
    ActivityModule::class], dependencies = [GithubComponent::class])
interface RepositoryComponent : AndroidInjector<RepositoryApp>{


    @Component.Builder
    interface Builder{

        fun githubComponent(component: GithubComponent) : Builder

        fun bindContext(@BindsInstance context: Context) : Builder

        fun create() : RepositoryComponent
    }
}

