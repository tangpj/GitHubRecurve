package com.tangpj.oauth2.di

import android.content.Context
import com.tangpj.github.GithubApp
import com.tangpj.github.di.GithubComponent
import com.tangpj.github.di.Retrofit2Module
import com.tangpj.github.di.ViewModelFactoryModule
import com.tangpj.oauth2.provider.TokenModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Modules单独运行时用到的Component
 *
 * @ClassName: Oauth2AppComponent
 * @author create by Tang
 * @date 2019/1/22 9:50 PM
 */
@Oauth2Scope
@Component(modules = [
    Retrofit2Module::class,
    AndroidSupportInjectionModule::class,
    DbModule::class,
    Oauth2Module::class,
    TokenModule::class], dependencies = [GithubComponent::class])
interface Oauth2AppComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    interface Builder{

        fun githubComponent(component: GithubComponent) : Builder

        fun bindContext(@BindsInstance context: Context) : Builder

        fun creator() : Oauth2AppComponent

    }
}