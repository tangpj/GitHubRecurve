package com.tangpj.oauth2.di

import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.tangpj.github.utils.RetrofitNextPageStrategy
import com.tangpj.oauth2.api.OAuthService
import com.tangpj.oauth2.ui.AuthorizationModule
import com.tangpj.recurve.retrofit2.LiveDataCallAdapterFactory
import dagger.Module

@Module(includes = [AuthorizationModule::class])
class Oauth2Module{

    @Provides
    fun providerOauthService(): OAuthService {
        return Retrofit.Builder()
                .baseUrl("https://github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory(RetrofitNextPageStrategy()))
                .build()
                .create(OAuthService::class.java)
    }
}