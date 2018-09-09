package com.tangpj.oauth2.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tangpj.oauth.R
import com.tangpj.oauth.databinding.ActivityOauth2Binding
import com.tangpj.oauth2.GithubOauth2.Companion.PARAM_CODE
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class Oauth2Activity: AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            dispatchingAndroidInjector

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

}