package com.tangpj.github.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import com.tangpj.github.GitHubApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class])
interface AppComponent: AndroidInjector<GitHubApp>{
    @Component.Builder
    interface Builder{
        @BindsInstance fun application(app: GitHubApp): AppComponent.Builder

        fun build(): AppComponent
    }
}