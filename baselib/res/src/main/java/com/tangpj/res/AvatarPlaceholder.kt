package com.tangpj.res

import androidx.annotation.DrawableRes

data class GlidePlaceholder(
        @DrawableRes
        val placeHolderRes: Int,
        @DrawableRes
        val fallbackRes: Int,
        @DrawableRes
        val errorRes: Int
)

fun avatarHolder() = GlidePlaceholder(
        R.mipmap.avatar_default,
        R.mipmap.avatar_default,
        R.mipmap.avatar_default
)
