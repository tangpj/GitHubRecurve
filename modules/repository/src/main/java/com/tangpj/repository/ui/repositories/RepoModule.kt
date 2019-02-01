package com.tangpj.repository.ui.repositories


import com.tangpj.repository.di.RepositoryScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RepoModule{



    @RepositoryScope
    @ContributesAndroidInjector
    abstract fun contributesRepositoriesFragment(): RepoFragment
}

