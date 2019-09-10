package com.tangpj.repository.di

import com.tangpj.github.di.GlideModule
import com.tangpj.repository.ui.detail.RepoDetailActivity
import com.tangpj.repository.ui.repositories.ReposActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{

    abstract fun contributeRepoActivity(): ReposActivity

    @ContributesAndroidInjector(modules = [GlideModule::class])
    abstract fun contributeRepoDetailActivity() : RepoDetailActivity
}