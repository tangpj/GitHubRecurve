package com.tangpj.github.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.tangpj.oauth.api.OAuthService

fun createOauthService(): OAuthService{
    val retrofit = Retrofit.Builder().baseUrl("https://github.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(OAuthService::class.java)

}