package com.tangpj.oauth2

import com.tangpj.github.BuildConfig
import com.tangpj.github.GithubApp
import com.tangpj.oauth2.di.DaggerOauth2AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber



/**
 * @ClassName: Oauth2App
 * @author create by Tang
 * @date 2018/9/7 下午2:42
 * @Description: 测试用Application，
 */
class Oauth2App : GithubApp(){


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(object : Timber.Tree(){
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    //release包不打印日志
                }
            })
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return  DaggerOauth2AppComponent.builder().create(this)
    }


}