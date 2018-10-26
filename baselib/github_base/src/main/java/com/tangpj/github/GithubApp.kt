package com.tangpj.github

import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

open class GithubApp: DaggerApplication(){

    private lateinit var instance: GithubApp

    @Inject
    lateinit var githubTokenDao: GithubTokenDao

    lateinit var token: LiveData<GithubToken>


    override fun onCreate() {
        super.onCreate()
        instance = this

        token = githubTokenDao.loadToken()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getInstance(): GithubApp = instance

}