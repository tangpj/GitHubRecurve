package com.tangpj.github.di

import androidx.lifecycle.LiveData
import com.tangpj.github.GithubApp
import com.tangpj.github.pojo.GithubToken
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class])
interface GithubComponent : AndroidInjector<GithubApp>{

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>() {
        @BindsInstance
        abstract fun token(token: LiveData<GithubToken>): Builder

    }

}