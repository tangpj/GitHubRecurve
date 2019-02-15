package com.tangpj.github.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.tangpj.github.GithubApp
import com.tangpj.github.R


const val THEME_ID = "theme_it"
private const val THEME_NAME = "theme_name"

fun installAppThemeSp(app: GithubApp) : SharedPreferences{
    return app.getSharedPreferences(THEME_NAME, MODE_PRIVATE)
}

fun installThemeId(app: GithubApp) : Int{
    val sharedPreferences = installAppThemeSp(app)
    return sharedPreferences.getInt(THEME_ID, R.style.AppTheme_Normal)
}