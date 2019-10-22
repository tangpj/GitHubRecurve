package com.tangpj.github.di

import android.content.Context
import com.tangpj.github.GithubApp
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@GithubScope
@Subcomponent(modules = [
    OkHttpModule::class,
    ViewModelFactoryModule::class])
interface GithubComponent{

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : GithubComponent
    }
}

@Module(subcomponents = [GithubComponent::class])
class GithubAppServerModule



