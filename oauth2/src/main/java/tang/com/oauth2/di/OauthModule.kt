package tang.com.oauth.di

import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tang.com.github.utils.GithubNextPageStrategy
import tang.com.oauth.api.OAuthService
import tang.com.recurve.util.LiveDataCallAdapterFactory
import javax.inject.Singleton

class OauthModule{

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
}