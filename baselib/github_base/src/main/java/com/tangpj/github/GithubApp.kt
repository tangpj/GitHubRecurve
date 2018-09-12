package com.tangpj.github

import android.app.Activity
import android.app.Application
import com.tangpj.recurve.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

abstract class GithubApp: Application(), HasActivityInjector {

    abstract fun componentInject()

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector()
                .setComponentInject { componentInject() }
                .init(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> =
            dispatchingAndroidInjector

}