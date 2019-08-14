package com.tangpj.github

import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tangpj.github.dataBinding.GithubComponent
import com.tangpj.github.utils.installThemeId
import dagger.android.support.DaggerApplication

abstract class GithubApp: DaggerApplication(){

    private var appThemeId: Int = 0

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
//        appThemeId = installThemeId(this)
        instance = this
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        DataBindingUtil.setDefaultComponent(GithubComponent())
    }

    companion object {
        private lateinit var instance: GithubApp
        fun getInstance(): GithubApp = instance
    }

    fun getAppThemeId() = appThemeId

}