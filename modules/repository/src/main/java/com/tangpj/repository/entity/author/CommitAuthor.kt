package com.tangpj.repository.entity.author

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tangpj.repository.entity.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class CommitAuthor(
        @PrimaryKey
        override val id: String,
        val email: String
) : BaseEntity(id)
