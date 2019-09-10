package com.tangpj.github.di

import android.app.Activity
import com.bumptech.glide.Glide
import com.tangpj.github.R
import com.tangpj.github.dataBinding.GlideDataBindingComponent
import dagger.Module
import dagger.Provides

@Module
class GlideModule{
    @Provides
    fun provideGlideComponent(activity: Activity) : GlideDataBindingComponent{
        val requestManager = Glide.with(activity)
        val component = GlideDataBindingComponent(requestManager)
        component.placeholderRes = R.drawable.ic_launcher_foreground
        component.errorRes = R.mipmap.ic_launcher
        component.fallbackRes = R.mipmap.ic_launcher_round
        return component
    }
}