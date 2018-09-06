package com.tangpj.github

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.tangpj.github.di.DaggerAppComponent

class GitHubApp: DaggerApplication() {

    companion object {
        private lateinit var sInstance: GitHubApp
        fun get(): GitHubApp{
            return sInstance
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    fun logout(){

    }


}