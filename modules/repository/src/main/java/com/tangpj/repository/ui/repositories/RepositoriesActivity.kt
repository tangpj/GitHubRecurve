package com.tangpj.repository.ui.repositories

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tangpj.github.BaseActivity
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepositoriesBinding

class RepositoriesActivity : BaseActivity(){

    lateinit var navController: NavController

    lateinit var activityRepositoriesBinding: ActivityRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRepositoriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_repositories)
        appbar{
            title = getString(R.string.app_name)
        }

    }



}