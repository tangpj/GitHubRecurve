package com.tangpj.repository.entry.actor

import androidx.room.Ignore
import com.tangpj.repository.entry.Entry
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Actor(
        @field:Ignore
        override val id : String,
        @field:Ignore
        open val login: String,
        @field:Ignore
        open val avatarUrl: String? = null
) : Entry(id)