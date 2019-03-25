package com.tangpj.github.di

import com.tangpj.recurve.retrofit2.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class Retrofit2Module{

    @Suppress("HasPlatformType")
    @Provides
    fun providerRetrofitBuilder()
            = Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

}
