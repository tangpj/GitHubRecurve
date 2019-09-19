package com.tangpj.github.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.tangpj.github.core.svg.SvgSoftwareLayerSetter

fun getDefaultRequestManager(context: Context) =
        Glide.with(context)
                .`as`(PictureDrawable::class.java)
                .transition(withCrossFade())
                .listener(SvgSoftwareLayerSetter())

