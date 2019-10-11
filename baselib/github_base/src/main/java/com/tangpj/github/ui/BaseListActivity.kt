package com.tangpj.github.ui

import android.os.Bundle
import com.recurve.dagger2.RecurveDaggerListActivity
import com.tangpj.github.GithubApp
import com.tangpj.github.R
import com.tangpj.github.utils.THEME_ID
import com.tangpj.github.utils.installAppThemeSp

open class BaseListActivity : RecurveDaggerListActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(GithubApp.getInstance().getAppThemeId())
        super.onCreate(savedInstanceState)
        installAppThemeSp(GithubApp.getInstance()).edit().putInt(THEME_ID, R.style.AppTheme_Night).apply()
    }
}