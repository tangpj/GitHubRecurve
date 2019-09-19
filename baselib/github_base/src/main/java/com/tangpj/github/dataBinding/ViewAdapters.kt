package com.tangpj.github.dataBinding

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter(value = [
    "android:drawableStart",
    "android:drawableTop",
    "android:drawableEnd",
    "android:drawableBottom"], requireAll = false)
fun TextView.setDrawableStartRes(
        @DrawableRes startResource: Int = 0,
        @DrawableRes topResource: Int = 0,
        @DrawableRes endResource: Int = 0,
        @DrawableRes bottomResource: Int = 0){

    val drawableStart = getDrawable(context, startResource)
    val drawableTop = getDrawable(context, topResource)
    val drawableEnd = getDrawable(context, endResource)
    val drawableBottom= getDrawable(context, bottomResource)
    this.setCompoundDrawables(drawableStart, drawableTop, drawableEnd, drawableBottom)
}

private fun getDrawable(context: Context, @DrawableRes res: Int) : Drawable?{
    if (res == 0){
        return null
    }
    val dw = context.getDrawable(res)
    dw?.setBounds(0 , 0, dw.minimumWidth, dw.minimumHeight)
    return dw
}



