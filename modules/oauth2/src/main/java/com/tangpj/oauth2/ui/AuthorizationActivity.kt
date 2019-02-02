package com.tangpj.oauth2.ui

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import com.tangpj.github.BaseActivity
import com.tangpj.oauth2.GithubOauth2.Companion.PARAM_CODE
import com.tangpj.oauth2.R


class AuthorizationActivity: BaseActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = initContentFragment(R.navigation.navigation_oauth2)
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