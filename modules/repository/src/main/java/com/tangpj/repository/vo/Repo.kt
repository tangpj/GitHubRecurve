package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(
        indices = [
            Index("id"),
            Index("owner_login")],
        primaryKeys = ["name", "owner_login"]
)
data class Repo @JvmOverloads constructor(
        val name: String,
        @Embedded(prefix = "owner_")
        val owner: Owner,
        val id: String = "0",
        val fullName: String? = null,
        val language: String? = null,
        val languageColor: String? = null,
        val description: String? = null,
        val stars: Int = 0,
        val forked: Int = 0
): Parcelable

@Parcelize
@Entity(primaryKeys = ["id","login"])
data class Owner(
        val id: String,
        val login: String,
        val url: String? = null
): Parcelable


enum class RepoType{
    STAR,
    FORK,
    WATCH,
    NORMAL
}
