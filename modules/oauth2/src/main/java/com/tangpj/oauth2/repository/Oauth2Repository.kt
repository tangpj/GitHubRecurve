package com.tangpj.oauth2.repository

import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.domain.GithubToken
import com.tangpj.oauth2.api.OAuthService
import com.tangpj.oauth2.request.RequestToken
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Oauth2Repository @Inject
constructor(val tokenDao: GithubTokenDao, val oauthService: OAuthService){

    private val tokenRateLimiter = RateLimiter<RequestToken>(10, TimeUnit.MINUTES)

    fun getGithubToken(requestToken: RequestToken) =
            object : NetworkBoundResource<GithubToken, GithubToken>(){
                override fun saveCallResult(item: GithubToken) {
                    tokenDao.insert(item)
                }

                override fun shouldFetch(data: GithubToken?): Boolean =
                        data == null || tokenRateLimiter.shouldFetch(requestToken)

                override fun loadFromDb(): LiveData<GithubToken> = tokenDao.loadToken()

                override fun createCall(): LiveData<ApiResponse<GithubToken>> =
                        oauthService.getToken(requestToken)

                override fun onFetchFailed() {
                    tokenRateLimiter.reset(requestToken)
                }

            }.asLiveData()

}
