package com.tangpj.repository.ui.repositories

import android.os.Bundle
import androidx.navigation.NavController
import com.tangpj.github.BaseActivity
import com.tangpj.repository.R

class RepositoriesActivity : BaseActivity(){

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = initContentFragment(
                R.navigation.navigation_repositories)
        appbar{
            title = getString(R.string.app_name)
        }

    }



}