package com.tangpj.github

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import com.tangpj.recurve.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

abstract class GithubApp: Application(), HasActivityInjector {

    private lateinit var instance: GithubApp

    @Inject
    lateinit var githubTokenDao: GithubTokenDao

    lateinit var token: LiveData<GithubToken>

    abstract fun componentInject()

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppInjector()
                .setComponentInject {

                    componentInject()
                }
                .init(this)
        token = githubTokenDao.loadToken()

    }


    override fun activityInjector(): AndroidInjector<Activity> =
            dispatchingAndroidInjector


    fun getInstance(): GithubApp = instance

}