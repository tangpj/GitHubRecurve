package com.tangpj.github

import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tangpj.github.dataBinding.GithubComponent
import com.tangpj.github.utils.installThemeId
import dagger.android.support.DaggerApplication
import javax.inject.Inject

abstract class GithubApp: DaggerApplication(){

    private var appThemeId: Int = 0

    var requestManager: RequestManager? = null

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
        appThemeId = installThemeId(this)
        instance = this
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
                requestManager?.let {
            DataBindingUtil.setDefaultComponent(GithubComponent())
        }
    }

    companion object {
        private lateinit var instance: GithubApp
        fun getInstance(): GithubApp = instance
    }

    fun getAppThemeId() = appThemeId

}