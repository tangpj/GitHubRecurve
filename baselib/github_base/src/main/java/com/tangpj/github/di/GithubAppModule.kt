package com.tangpj.github.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [
    OkHttpModule::class,
    DbModule::class,
    ReceiversModule::class,
    AndroidSupportInjectionModule::class])
class GithubAppModule