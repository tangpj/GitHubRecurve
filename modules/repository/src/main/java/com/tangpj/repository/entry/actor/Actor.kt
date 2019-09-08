package com.tangpj.repository.entry.actor

import com.tangpj.repository.entry.Entry
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Actor(
        override
        val id : String,
        open val login: String,
        open val avatarUrl: String? = null
) : Entry(id)