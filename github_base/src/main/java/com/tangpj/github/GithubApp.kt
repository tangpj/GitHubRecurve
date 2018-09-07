package com.tangpj.github

import android.app.Activity
import android.app.Application
import com.tangpj.recurve.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

abstract class GithubApp: Application(), HasActivityInjector {

    abstract fun initComponentInject()

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector()
                .setComponentInject { initComponentInject() }
                .init(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> =
            dispatchingAndroidInjector

}