package com.tangpj.mvvmrecurve

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tangpj.mvvmrecurve.databinding.ActivityMainBinding
import com.tangpj.oauth2.GithubOauth2
import com.tangpj.recurve.widget.ModulesAdapter

class MainActivity : AppCompatActivity() {

    private val adapter = ModulesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)


    }

    override fun onNewIntent(intent: Intent?) {
        GithubOauth2.getGithubToken(intent, null)
        super.onNewIntent(intent)

    }

    fun add(v: View){
        GithubOauth2.launchOauthLogin(this)
    }
}
