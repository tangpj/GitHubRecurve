package com.tangpj.github

import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.utils.installThemeId
import dagger.android.support.DaggerApplication
import javax.inject.Inject

abstract class GithubApp: DaggerApplication(){

    private var appThemeId: Int = 0

    @Inject
    lateinit var githubTokenDao: GithubTokenDao

    override fun onCreate() {
        super.onCreate()
        appThemeId = installThemeId(this)
        instance = this
    }

    companion object {
        private var instance: GithubApp? = null
        fun getInstance(): GithubApp = instance!!
    }

    fun getAppThemeId() = appThemeId

}