package com.tangpj.github.di

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tangpj.github.R
import dagger.Module
import dagger.Provides

@Module
class GithubFragmentBindingModule{
    @Provides
    fun provideGlideComponent(fragment: Fragment) : GithubBindingComponent {
        val requestManager = Glide.with(fragment)
        val component = GithubBindingComponent(requestManager)
        component.placeholderRes = R.drawable.ic_launcher_foreground
        component.errorRes = R.mipmap.ic_launcher
        component.fallbackRes = R.mipmap.ic_launcher_round
        return component
    }


}