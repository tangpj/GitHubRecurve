package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id","login"])
data class Owner(
        val id: String,
        val login: String,
        val url: String? = null
): Parcelable