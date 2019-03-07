package com.tangpj.repository.domain

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id","login"])
data class OwnerDo(
        val id: String,
        val login: String,
        val avatarUrl: String? = null
): Parcelable