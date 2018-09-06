package com.tangpj.oauth2.ui

import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.github.pojo.GithubToken
import com.tangpj.oauth.BuildConfig
import com.tangpj.oauth.repository.Oauth2Repository
import com.tangpj.oauth2.GithubOauth2
import com.tangpj.recurve.resource.Resource
import javax.inject.Inject


class Oauth2ViewModel @Inject constructor(repository: Oauth2Repository): ViewModel() {
    private val codeUri: MutableLiveData<Uri> = MutableLiveData()

    val token: LiveData<Resource<GithubToken>> = Transformations
            .switchMap(codeUri){
                repository.getGithubToken(it)
            }

    fun refreshCode(uri: Uri) {
        codeUri.value = uri
    }

    fun buildAuthorizeUri(): Uri
            = Uri.parse(GithubOauth2.OAUTH_URL)
                .buildUpon()
                .appendQueryParameter(GithubOauth2.PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
                .appendQueryParameter(GithubOauth2.PARAM_SCOPE, BuildConfig.SCOPES)
                .appendQueryParameter(GithubOauth2.PARAM_CALLBACK_URI, BuildConfig.REDIRECTt_URI)
                .build()








}