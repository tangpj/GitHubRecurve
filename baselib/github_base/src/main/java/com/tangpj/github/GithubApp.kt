package com.tangpj.github

import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import dagger.android.DaggerApplication
import javax.inject.Inject

abstract class GithubApp: DaggerApplication(){

    private lateinit var instance: GithubApp

    @Inject
    lateinit var githubTokenDao: GithubTokenDao

    lateinit var token: LiveData<GithubToken>


    override fun onCreate() {
        super.onCreate()
        instance = this
        token = githubTokenDao.loadToken()
    }

    fun getInstance(): GithubApp = instance

}