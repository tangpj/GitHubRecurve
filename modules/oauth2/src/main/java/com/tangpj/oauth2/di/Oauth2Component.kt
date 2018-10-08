package com.tangpj.oauth2.di

import android.app.Application
import com.tangpj.github.GithubApp
import com.tangpj.oauth.di.OauthAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            OauthAppModule::class,
            Oauth2ActivityModule::class])
interface Oauth2Component{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): Oauth2Component
    }

    fun inject(app: GithubApp)
}