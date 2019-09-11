package com.tangpj.repository.di

import android.app.Activity
import com.tangpj.github.di.GithubActivityBindingModules
import com.tangpj.github.di.GithubFragmentBindingModule
import com.tangpj.repository.ui.detail.RepoDetailActivity
import com.tangpj.repository.ui.repositories.ReposActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, GithubActivityBindingModules::class])
    abstract fun contributeRepoActivity(): ReposActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeRepoDetailActivity() : RepoDetailActivity

    @Binds
    abstract fun bindReposActivity(reposActivity: ReposActivity) : Activity
}