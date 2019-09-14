package com.tangpj.github.dataBinding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun ImageView.setImageSrc(@DrawableRes resource: Int){
    setImageResource(resource)
}