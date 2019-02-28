package com.tangpj.oauth2.api

import androidx.lifecycle.LiveData
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.tangpj.github.po.GithubToken
import com.tangpj.oauth2.request.RequestToken
import com.tangpj.recurve.resource.ApiResponse

interface OAuthService {

    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    fun getToken(@Body var1: RequestToken): LiveData<ApiResponse<GithubToken>>
}