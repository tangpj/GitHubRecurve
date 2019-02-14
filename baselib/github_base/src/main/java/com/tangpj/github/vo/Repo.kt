package com.tangpj.github.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Entity(
        indices = [
            Index("id"),
            Index("owner_login")],
        primaryKeys = ["name", "owner_login"]
)

@Parcelize
data class Repo(
        val name: String,
        val id: String = "0",
        val fullName: String? = null,
        val language: String? = null,
        val languageColor: String? = null,
        val description: String? = null,
        @Embedded(prefix = "owner_")
        val owner: Owner = Owner("recurve"),
        val stars: Int = 0,
        val forked: Int = 0
): Parcelable

@Parcelize
data class Owner(
        val login: String,
        val url: String? = null
): Parcelable