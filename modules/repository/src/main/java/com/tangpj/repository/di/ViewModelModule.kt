package com.tangpj.repository.di

import androidx.lifecycle.ViewModel
import com.tangpj.recurve.dagger2.ViewModelKey
import com.tangpj.repository.ui.detail.RepoDetailViewModel
import com.tangpj.repository.ui.detail.viewer.ViewerViewModel
import com.tangpj.repository.ui.detail.files.FilesViewModel
import com.tangpj.repository.ui.repositories.ReposViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(ReposViewModel::class)
    abstract fun bindRepositoryViewModule(reposViewModel: ReposViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailViewModel::class)
    abstract fun bindRepoDetailViewModule(repoDetailViewModel: RepoDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewerViewModel::class)
    abstract fun bindFileContentViewModel(viewerViewModel: ViewerViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilesViewModel::class)
    abstract fun bindFilesViewModel(filesViewModel: FilesViewModel) : ViewModel
}