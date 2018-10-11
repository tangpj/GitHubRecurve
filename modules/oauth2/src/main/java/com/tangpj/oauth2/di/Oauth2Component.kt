package com.tangpj.oauth2.di

import android.app.Application
import com.tangpj.github.di.GithubComponent
import com.tangpj.oauth2.Oauth2App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Oauth2Scope
@Component(
        modules = [
            OauthAppModule::class,
            Oauth2ActivityModule::class], dependencies = [ GithubComponent::class ])
interface Oauth2Component{


    @Component.Builder
    interface Builder{

        fun githubComponent(githubComment: GithubComponent.Builder): Builder


        fun build(): Oauth2Component
    }

    fun inject(oauth2App: Oauth2App)

}