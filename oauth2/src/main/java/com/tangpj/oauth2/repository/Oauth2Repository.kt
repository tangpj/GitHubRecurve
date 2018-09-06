package com.tangpj.oauth.repository

import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.oauth.BuildConfig
import com.tangpj.oauth.api.OAuthService
import javax.inject.Inject

class Oauth2Repository @Inject
constructor(val db: GithubDb, val dao: GithubTokenDao, val oauthService: OAuthService){

    fun getGithubToken(){

    }
}
