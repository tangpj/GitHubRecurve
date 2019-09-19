package com.tangpj.github.utils

import android.app.Activity
import android.content.ContextWrapper
import android.view.View

fun View.getActivity() : Activity?{
    var tempContext = context
    while (tempContext is ContextWrapper){
        if (tempContext is Activity){
            return tempContext
        }
        tempContext = tempContext.baseContext
    }
    return null
}