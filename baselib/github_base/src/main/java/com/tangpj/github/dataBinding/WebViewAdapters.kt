package com.tangpj.github.dataBinding

import android.webkit.WebView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["data"], requireAll = false)
fun WebView.loadData(data: String?=""){
    loadData(data, "text/html", "UTF-8")
}

@BindingAdapter(value = ["javaScript","adaptive"], requireAll = false)
fun WebView.settings(javaScript: Boolean?, adaptive: Boolean?){
    javaScript?.let {
        settings.javaScriptEnabled = it
    }
    adaptive?.let {
        settings.useWideViewPort = it
        settings.loadWithOverviewMode = it
    }
}

