package com.tangpj.github.di

import androidx.lifecycle.LiveData
import com.tangpj.github.GithubApp
import com.tangpj.github.pojo.GithubToken
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class])
interface GithubComponent{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(app: GithubApp): Builder

        @BindsInstance
        fun token(token: LiveData<GithubToken>): Builder

        fun build(): GithubComponent
    }

    fun inject(app: GithubApp)
}