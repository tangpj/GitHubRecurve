package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.tangpj.repository.domain.OwnerDo
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(
        indices = [
            Index("id"),
            Index("owner_login")],
        primaryKeys = ["name", "owner_login"]
)
data class RepoVo @JvmOverloads constructor(
        val id: Int,
        val name: String,
        @Embedded(prefix = "owner_")
        val owner: OwnerDo,
        val fullName: String? = null,
        val language: String? = null,
        val languageColor: String? = null,
        val description: String? = null,
        val stars: Int = 0,
        val forked: Int = 0
): Parcelable