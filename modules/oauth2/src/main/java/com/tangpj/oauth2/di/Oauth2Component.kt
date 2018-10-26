package com.tangpj.oauth2.di

import com.tangpj.github.GithubApp
import com.tangpj.github.di.GithubComponent
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjector

interface Oauth2Component : AndroidInjector<GithubApp>{

    abstract class Builder : AndroidInjector.Builder<GithubApp>(){

    }
}