package com.tangpj.repository.ui.repositories

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tangpj.repository.R
import com.tangpj.repository.di.RepositoryScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class RepositoriesModule{

    @RepositoryScope
    @ContributesAndroidInjector
    abstract fun contributesRepositoriesActivity() : RepositoriesActivity

    @Provides
    fun providesNavController(activity: RepositoriesActivity): NavController =
            activity.findNavController(R.navigation.navigation_repositories)

}

