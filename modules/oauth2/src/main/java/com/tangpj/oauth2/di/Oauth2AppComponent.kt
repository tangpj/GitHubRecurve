package com.tangpj.oauth2.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.GithubAppModule
import com.tangpj.github.di.Retrofit2Module
import com.tangpj.github.di.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Modules单独运行时用到的Component
 *
 * @ClassName: Oauth2AppComponent
 * @author create by Tang
 * @date 2019/1/22 9:50 PM
 */
@Singleton
@Component(modules = [
    GithubAppModule::class,
    ViewModelFactoryModule::class,
    Retrofit2Module::class,
    DbModule::class,
    Oauth2Module::class
])
interface Oauth2AppComponent : AndroidInjector<GithubApp>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>()
}