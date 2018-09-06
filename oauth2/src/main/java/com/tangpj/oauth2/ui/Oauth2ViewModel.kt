package com.tangpj.oauth2.ui

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tangpj.github.pojo.GithubToken
import com.tangpj.oauth.BuildConfig
import com.tangpj.oauth2.GithubOauth2
import com.tangpj.recurve.util.openInCustomTabOrBrowser


class Oauth2ViewModel: ViewModel() {
    private val token: MutableLiveData<GithubToken> = MutableLiveData()

    private fun buildAuthorizeUri(): Uri
            = Uri.parse(GithubOauth2.OAUTH_URL)
                .buildUpon()
                .appendQueryParameter(GithubOauth2.PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
                .appendQueryParameter(GithubOauth2.PARAM_SCOPE, BuildConfig.SCOPES)
                .appendQueryParameter(GithubOauth2.PARAM_CALLBACK_URI, BuildConfig.REDIRECTt_URI)
                .build()


    fun authorize(view: View){
        val uri = buildAuthorizeUri()
        openInCustomTabOrBrowser(view.context, uri)
    }
}