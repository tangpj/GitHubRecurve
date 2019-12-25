package com.tangpj.repository.di

import android.content.Context
import com.tangpj.github.GithubApp
import com.tangpj.github.di.*
import com.tangpj.repository.RepositoryApp
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@RepositoryScope
@Component(modules = [
    RepositoryModule::class,
    AndroidSupportInjectionModule::class,
    ApolloModule::class,
    ActivityModule::class], dependencies = [GithubComponent::class])
interface RepositoryComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    interface Builder{

        fun githubComponent(component: GithubComponent) : Builder

        fun bindContext(@BindsInstance context: Context) : Builder

        fun create() : RepositoryComponent
    }
}

