package com.tangpj.oauth2.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.github.BuildConfig
import com.tangpj.oauth2.domain.GithubToken
import com.tangpj.oauth2.repository.Oauth2Repository
import com.tangpj.oauth2.GithubOauth2
import com.tangpj.oauth2.request.RequestToken
import com.tangpj.recurve.resource.Resource
import javax.inject.Inject


class AuthorizationViewModel @Inject constructor(repository: Oauth2Repository): ViewModel() {
    private val requestToken: MutableLiveData<RequestToken> = MutableLiveData()

    val token: LiveData<Resource<GithubToken>> = Transformations
            .switchMap(requestToken){
                repository.getGithubToken(it)
            }

    fun refreshCode(code: String) {
        requestToken.value = RequestToken(code = code)
    }

    fun buildAuthorizeUri(): Uri
            = Uri.parse(GithubOauth2.OAUTH_URL)
                .buildUpon()
                .appendQueryParameter(GithubOauth2.PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
                .appendQueryParameter(GithubOauth2.PARAM_SCOPE, BuildConfig.SCOPES)
                .appendQueryParameter(GithubOauth2.PARAM_CALLBACK_URI, BuildConfig.REDIRECTt_URI)
                .build()

}