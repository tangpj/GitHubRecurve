package com.tangpj.oauth.di

import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.tangpj.github.utils.GithubNextPageStrategy
import com.tangpj.oauth.api.OAuthService
import com.tangpj.oauth2.di.Oauth2ViewModelModule
import com.tangpj.recurve.util.LiveDataCallAdapterFactory
import dagger.Module
import javax.inject.Singleton

@Module(includes = [Oauth2ViewModelModule::class])
class OauthAppModule{

    @Singleton
    @Provides
    fun providerOauthService(): OAuthService{
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory(GithubNextPageStrategy()))
                .build()
                .create(OAuthService::class.java)
    }

    @Singleton
    @Provides
    fun providerGithubTokenDao(githubDb: GithubDb): GithubTokenDao =
            githubDb.tokenDao()
}