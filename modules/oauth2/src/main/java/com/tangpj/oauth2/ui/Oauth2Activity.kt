package com.tangpj.oauth2.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.tangpj.oauth2.GithubOauth2.Companion.PARAM_CODE
import com.tangpj.oauth2.R
import com.tangpj.oauth2.databinding.ActivityOauth2Binding
import dagger.android.support.DaggerAppCompatActivity


class Oauth2Activity: DaggerAppCompatActivity() {


    lateinit var binding: ActivityOauth2Binding

    lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_oauth2)
        navController = findNavController(R.id.fragment_container)
        intent?.data?.getQueryParameter(PARAM_CODE)?.let {
            navController.navigate(Oauth2FragmentDirections.refreshCode().setCode(it))
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter(PARAM_CODE)?.let {
            navController.navigate(Oauth2FragmentDirections.refreshCode().setCode(it))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

}