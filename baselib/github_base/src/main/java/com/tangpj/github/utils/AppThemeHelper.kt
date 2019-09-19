package com.tangpj.github.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.tangpj.github.GithubApp
import com.tangpj.github.R


const val THEME_ID = "theme_it"
private const val THEME_NAME = "theme_name"

fun installAppThemeSp(context: Context) : SharedPreferences{
    return context.getSharedPreferences(THEME_NAME, MODE_PRIVATE)
}

fun installThemeId(context: Context) : Int{
    val sharedPreferences = installAppThemeSp(context)
    return sharedPreferences.getInt(THEME_ID, R.style.AppTheme_Normal)
}

fun isNightTheme(context: Context) : Boolean{
    val sharedPreferences = installAppThemeSp(context)
    return sharedPreferences.getInt(THEME_ID, R.style.AppTheme_Normal) == R.style.AppTheme_DayNight
}