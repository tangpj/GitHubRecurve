package com.tangpj.repository.di

import com.tangpj.repository.ui.repositories.RepoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeRepoActivity(): RepoActivity

}