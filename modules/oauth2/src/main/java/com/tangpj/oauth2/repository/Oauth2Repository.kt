package com.tangpj.oauth2.repository

import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import com.tangpj.oauth.api.OAuthService
import com.tangpj.oauth2.request.RequestToken
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetWorkBoundResource
import com.tangpj.recurve.resource.Resource
import com.tangpj.recurve.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Oauth2Repository @Inject
constructor(val gitDb: GithubDb, val tokenDao: GithubTokenDao, val oauthService: OAuthService){

    private val tokenRateLimiter = RateLimiter<RequestToken>(10, TimeUnit.MINUTES)

    fun getGithubToken(requestToken: RequestToken) : LiveData<Resource<GithubToken>> {

        return object : NetWorkBoundResource<GithubToken, GithubToken>(){
            override fun saveCallResult(item: GithubToken) {
                tokenDao.insert(item)
            }

            override fun shouldFetch(data: GithubToken?): Boolean =
                    data == null || tokenRateLimiter.shouldFetch(requestToken)

            override fun loadFromDb(): LiveData<GithubToken> = tokenDao.loadToken(1)

            override fun createCall(): LiveData<ApiResponse<GithubToken>> =
                oauthService.getToken(requestToken)

            override fun onFetchFailed() {
                tokenRateLimiter.reset(requestToken)
            }

        }.asLiveData()
    }
}
