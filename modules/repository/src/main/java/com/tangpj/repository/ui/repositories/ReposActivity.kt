package com.tangpj.repository.ui.repositories

import android.os.Bundle
import androidx.navigation.NavController
import com.tangpj.github.di.GithubBindingComponent
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.entity.domain.RepoFlag
import com.tangpj.repository.R
import javax.inject.Inject

class ReposActivity : BaseActivity(){

    private lateinit var navController: NavController

    @Inject lateinit var glide: GithubBindingComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = initContentFragment(
                R.navigation.repositories)


        val defaultArgs = ReposFragmentArgs
                .Builder()
                .setLogin("Tangpj")
                .setType(RepoFlag.STAR)
                .build()
                .toBundle()

        navController.navigate(R.id.repositories, defaultArgs)
        appbar{
            title = getString(R.string.app_name)
        }
    }
}