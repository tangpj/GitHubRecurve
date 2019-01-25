package com.tangpj.repository.ui.repositories

import android.os.Bundle
import androidx.navigation.NavController
import com.tangpj.github.BaseActivity
import com.tangpj.repository.R
import javax.inject.Inject

class RepositoriesActivity : BaseActivity(){

    @Inject
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appbar{
            title = getString(R.string.app_name)
        }

    }



}