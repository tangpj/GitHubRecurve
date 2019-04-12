package com.tangpj.github.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [
    OkHttpModule::class])
class GithubAppModule