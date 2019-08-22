package com.tangpj.repository.entry.vo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Ignore
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Entry(
        @Ignore
        open val id: String = "") : Parcelable