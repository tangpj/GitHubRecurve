package com.tangpj.repository.ui.repositories


import androidx.lifecycle.ViewModel
import com.tangpj.recurve.dagger2.ViewModelKey
import com.tangpj.repository.di.RepositoryScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class RepoModule{

    @RepositoryScope
    @ContributesAndroidInjector
    abstract fun contributesRepositoriesFragment(): RepoFragment

    @Binds @IntoMap @ViewModelKey(RepositoryViewModel::class)
    abstract fun bindRepositoryViewModule(repositoryViewModel: RepositoryViewModel): ViewModel

}

