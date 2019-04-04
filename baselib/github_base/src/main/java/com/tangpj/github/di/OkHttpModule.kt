package com.tangpj.github.di

import android.net.Uri
import androidx.loader.content.CursorLoader
import com.tangpj.github.GithubApp
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class OkHttpModule{

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: Interceptor): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build()


    @Singleton
    @Provides
    fun providerTokenInterceptor(app: GithubApp): Interceptor{
        return Interceptor {
//            val token = tokenDao.loadTokenForIO()
            val original: Request = it.request()
            val requestBuilder = original.newBuilder()
            val uri = Uri.parse(
                    "content://com.tangpj.oauth2.provider.tokenProvider/github_token")



//            token?.let {
//                Timber.d("set authorization header")
//                requestBuilder.addHeader("Authorization","token ${token.accessToken}")
//            }

            it.proceed(requestBuilder.build())
        }
    }

}