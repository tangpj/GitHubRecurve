package com.tangpj.github.dataBinding

import android.webkit.WebView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["html"], requireAll = false)
fun WebView.loadHtml(html: String?=""){
    loadData(html, "text/html", "UTF-8")
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

