package com.tangpj.github.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.tangpj.github.R

fun copyToClipboard(view: View, uri: String) {
    val context = view.context
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText(context.getString(R.string.app_name), uri)
    clipboard?.primaryClip = clip
    Snackbar.make(view,R.string.success_copied, Snackbar.LENGTH_SHORT).show()
}

