package com.tangpj.repository.valueObject

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id","login"])
data class Owner(
        var id: String,
        var login: String,
        var avatarUrl: String? = null
): Parcelable