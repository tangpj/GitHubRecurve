package com.tangpj.oauth2.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tangpj.github.BaseActivity
import com.tangpj.oauth2.GithubOauth2.Companion.PARAM_CODE
import com.tangpj.oauth2.R
import com.tangpj.oauth2.databinding.ActivityOauth2Binding


class AuthorizationActivity: BaseActivity() {


    lateinit var binding: ActivityOauth2Binding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = initContentFragment<ActivityOauth2Binding>(R.navigation.navigation_oauth2)
        intent?.data?.getQueryParameter(PARAM_CODE)?.let {
            navController.navigate(AuthorizationFragmentDirections.refreshCode().setCode(it))
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter(PARAM_CODE)?.let {
            navController.navigate(AuthorizationFragmentDirections.refreshCode().setCode(it))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

}