package com.tangpj.repository.ui.repositories


import com.tangpj.repository.di.RepositoryScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RepositoriesModule{

    @RepositoryScope
    @ContributesAndroidInjector
    abstract fun contributesRepositoriesActivity(): RepositoriesActivity

    @RepositoryScope
    @ContributesAndroidInjector
    abstract fun contributesRepositoriesFragment(): RepositoriesFragment
}
