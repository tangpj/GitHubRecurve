package com.tangpj.github.di

import android.net.Uri
import androidx.loader.content.CursorLoader
import com.tangpj.github.GithubApp
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class OkHttpModule{

    companion object {
        const val TOKEN_AUTHORITY = "com.tangpj.oauth2.provider.tokenProvider"
    }

    @Provides
    fun provideOkHttpClient(tokenInterceptor: Interceptor): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .callTimeout(3, TimeUnit.SECONDS)
            .build()


    @Provides
    fun providerTokenInterceptor(app: GithubApp): Interceptor{
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder = original.newBuilder()
                val uri = Uri.parse(
                        "content://$TOKEN_AUTHORITY/github_token")
                val resolver = app.contentResolver.query(
                        uri,
                        arrayOf("name"),
                        null, null, null)
                resolver?.use { cursor ->
                    val token = if(cursor.moveToFirst()){
                        cursor.getString(cursor.getColumnIndexOrThrow("accessToken"))
                    }else{
                        null
                    }
                    token?.let {
                        Timber.d("set authorization header")
                        requestBuilder.addHeader("Authorization","token $token")
                    }
                }

                return chain.proceed(requestBuilder.build())
            }

        }
    }

}