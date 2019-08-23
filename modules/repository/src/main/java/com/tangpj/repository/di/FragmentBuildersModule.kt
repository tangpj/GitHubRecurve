package com.tangpj.repository.di

import com.tangpj.repository.ui.detail.viewer.ViewerFragment
import com.tangpj.repository.ui.detail.files.FilesFragment
import com.tangpj.repository.ui.repositories.ReposFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule{

    @ContributesAndroidInjector
    abstract fun contributesRepositoriesFragment(): ReposFragment

    @ContributesAndroidInjector
    abstract fun contributesFilesFragment() : FilesFragment

    @ContributesAndroidInjector
    abstract fun contributesFileContentFragment(): ViewerFragment
}