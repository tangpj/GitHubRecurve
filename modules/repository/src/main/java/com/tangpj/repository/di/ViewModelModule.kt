package com.tangpj.repository.di

import androidx.lifecycle.ViewModel
import com.tangpj.recurve.dagger2.ViewModelKey
import com.tangpj.repository.ui.detail.RepoDetailViewModel
import com.tangpj.repository.ui.detail.fileContent.FileContentViewModel
import com.tangpj.repository.ui.repositories.RepositoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(RepositoryViewModel::class)
    abstract fun bindRepositoryViewModule(repositoryViewModel: RepositoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailViewModel::class)
    abstract fun bindRepoDetailViewModule(repoDetailViewModel: RepoDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FileContentViewModel::class)
    abstract fun bindFileContentViewModel(fileContentViewModel: FileContentViewModel) : ViewModel
}