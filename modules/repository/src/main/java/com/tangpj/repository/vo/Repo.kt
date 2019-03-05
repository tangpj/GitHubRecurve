package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.tangpj.github.domain.Default.Companion.UNKNOWN_ID
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(
        indices = [
            Index("id"),
            Index("owner_login")],
        primaryKeys = ["name", "owner_login"]
)
data class Repo @JvmOverloads constructor(
        val id: Int,
        val name: String,
        @Embedded(prefix = "owner_")
        val owner: Owner,
        val fullName: String,
        val language: String? = null,
        val languageColor: String? = null,
        val description: String? = null,
        val stars: Int = 0,
        val forked: Int = 0
): Parcelable