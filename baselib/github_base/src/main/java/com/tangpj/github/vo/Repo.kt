package com.tangpj.github.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(
        indices = [
            Index("id")
            , Index("owner_login")],
        primaryKeys = ["name", "owner_login"]
)

@Parcelize
data class Repo(
        val id: Int,
        val name: String,
        @field:SerializedName("full_name")
        val fullName: String,
        @field:SerializedName("description")
        val description: String,
        @Embedded(prefix = "owner_")
        val owner: Owner,
        @field:SerializedName("stargazers_count")
        val stars: Int
): Parcelable

@Parcelize
data class Owner(
        val login: String,
        val url: String?
): Parcelable