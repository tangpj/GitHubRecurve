package com.tangpj.oauth2.di

import dagger.Provides
import retrofit2.Retrofit
import com.tangpj.oauth2.api.OAuthService
import com.tangpj.oauth2.ui.AuthorizationModule
import dagger.Module

@Module(includes = [AuthorizationModule::class])
class Oauth2Module{

    @Provides
    fun providerOauthService(retrofit: Retrofit): OAuthService {
        return retrofit.create(OAuthService::class.java)
    }
}