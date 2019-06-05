package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.tangpj.repository.domain.Owner
import com.tangpj.repository.fragment.RepoDto
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(
        indices = [
            Index("id")],
        primaryKeys = ["name"]
)
data class Repo constructor(
        val id: String,
        @Embedded(prefix = "owner")
        val owner: Owner,
        val name: String,
        val fullName: String,
        val language: String,
        val languageColor: String,
        val description: String,
        val stars: Int,
        val forks: Int
): Parcelable



