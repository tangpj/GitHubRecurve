package com.tangpj.repository.di

import com.tangpj.repository.ui.detail.RepoDetailActivity
import com.tangpj.repository.ui.repositories.ReposActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeRepoActivity(): ReposActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeRepoDetailActivity() : RepoDetailActivity

}