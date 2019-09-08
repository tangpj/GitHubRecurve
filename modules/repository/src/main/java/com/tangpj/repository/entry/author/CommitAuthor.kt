package com.tangpj.repository.entry.author

import com.tangpj.repository.entry.Entry
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommitAuthor(
        override val id: String,
        val email: String
) : Entry(id)