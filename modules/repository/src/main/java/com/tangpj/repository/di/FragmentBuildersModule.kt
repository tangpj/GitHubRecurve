package com.tangpj.repository.di

import com.tangpj.repository.ui.detail.fileContent.FileContentFragment
import com.tangpj.repository.ui.repositories.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule{

    @ContributesAndroidInjector
    abstract fun contributesRepositoriesFragment(): RepoFragment

    @ContributesAndroidInjector
    abstract fun contributesFileContentFragment(): FileContentFragment
}