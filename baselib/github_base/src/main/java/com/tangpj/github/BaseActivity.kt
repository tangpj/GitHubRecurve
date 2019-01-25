package com.tangpj.github

import android.os.Bundle
import com.tangpj.github.di.THEME_ID
import com.tangpj.github.di.installAppThemeSp
import com.tangpj.recurve.dagger2.RecurveDaggerActivity

abstract class BaseActivity : RecurveDaggerActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(GithubApp.getInstance().getAppThemeId())
        super.onCreate(savedInstanceState)
        installAppThemeSp(GithubApp.getInstance()).edit().putInt(THEME_ID,R.style.AppTheme_Night).apply()
    }
}