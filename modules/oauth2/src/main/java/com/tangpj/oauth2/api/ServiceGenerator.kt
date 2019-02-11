package com.tangpj.oauth2.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

fun createOauthService(): OAuthService{
    val retrofit = Retrofit.Builder().baseUrl("https://github.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(OAuthService::class.java)

}