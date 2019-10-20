package com.tangpj.github.di

import com.tangpj.github.GithubApp
import dagger.Component
import dagger.android.AndroidInjector
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [GithubAppModule::class])
interface GithubComponent : AndroidInjector<GithubApp>{

    fun okhttp3() : OkHttpClient

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>(){

    }

}