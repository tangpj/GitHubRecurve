package com.tangpj.github.di

import android.app.Activity
import com.bumptech.glide.Glide
import com.tangpj.github.R
import dagger.Module
import dagger.Provides

@Module
class GithubActivityBindingModules {

    @Provides
    fun provideGlideComponentByActivity(activity: Activity) : GithubBindingComponent {
        val requestManager = Glide.with(activity)
        val component = GithubBindingComponent(requestManager)
        component.placeholderRes = R.drawable.ic_launcher_foreground
        component.errorRes = R.mipmap.ic_launcher
        component.fallbackRes = R.mipmap.ic_launcher_round
        return component
    }
}