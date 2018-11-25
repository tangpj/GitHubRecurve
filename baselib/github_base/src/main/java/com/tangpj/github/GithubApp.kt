package com.tangpj.github

import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.di.installThemeId
import com.tangpj.github.pojo.GithubToken
import dagger.android.DaggerApplication
import javax.inject.Inject

abstract class GithubApp: DaggerApplication(){

    private var appThemeId: Int = 0

    @Inject
    lateinit var githubTokenDao: GithubTokenDao

    lateinit var token: LiveData<GithubToken>


    override fun onCreate() {
        super.onCreate()
        appThemeId = installThemeId(this)
        instance = this
        token = githubTokenDao.loadToken()
    }

    companion object {
        private var instance: GithubApp? = null
        fun getInstance(): GithubApp = instance!!
    }

    fun getAppThemeId() = appThemeId

}