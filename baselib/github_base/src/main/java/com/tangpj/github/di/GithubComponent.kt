package com.tangpj.github.di

import android.content.Context
import dagger.*
import okhttp3.OkHttpClient

@GithubScope
@Component(modules = [
    OkHttpModule::class,
    ViewModelFactoryModule::class])
interface GithubComponent{

    fun okHttp() : OkHttpClient

    @Component.Factory
    interface Factory{
       fun create(@BindsInstance context: Context) : GithubComponent
    }
}


