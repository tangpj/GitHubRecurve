package com.tangpj.github

import android.content.SharedPreferences
import android.os.Bundle
import com.tangpj.github.di.THEME_ID
import com.tangpj.github.di.installAppThemeSp
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(GithubApp.getInstance().getAppThemeId())
        super.onCreate(savedInstanceState)
        installAppThemeSp(GithubApp.getInstance()).edit().putInt(THEME_ID,R.style.AppTheme_Night).apply()
    }
}