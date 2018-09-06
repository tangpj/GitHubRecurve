package com.tangpj.oauth.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import com.tangpj.oauth.BuildConfig
import com.tangpj.oauth.api.OAuthService
import com.tangpj.oauth2.GithubOauth2
import com.tangpj.oauth2.request.RequestToken
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.resource.Resource
import com.tangpj.recurve.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Oauth2Repository @Inject
constructor(val gitDb: GithubDb, val tokenDao: GithubTokenDao, val oauthService: OAuthService){

    private val tokenRateLimiter = RateLimiter<Uri>(10, TimeUnit.MINUTES)

    fun getGithubToken(uri: Uri) : LiveData<Resource<GithubToken>> {
        val requestToken = RequestToken(clientId = BuildConfig.CLIENT_ID,
                client_secret = BuildConfig.CLIENT_SECRET,
                code = uri.getQueryParameter(GithubOauth2.PARAM_CODE) ?: "")
        return object : NetworkBoundResource<GithubToken, GithubToken>(){
            override fun saveCallResult(item: GithubToken) {
                item.code = requestToken.code
                tokenDao.insert(item)
            }

            override fun shouldFetch(data: GithubToken?): Boolean =
                    data == null || tokenRateLimiter.shouldFetch(uri)

            override fun loadFromDb(): LiveData<GithubToken> = tokenDao.loadToken(requestToken.code)

            override fun createCall(): LiveData<ApiResponse<GithubToken>> =
                oauthService.getToken(requestToken)

            override fun onFetchFailed() {
                tokenRateLimiter.reset(uri)
            }

        }.asLiveData()
    }
}
