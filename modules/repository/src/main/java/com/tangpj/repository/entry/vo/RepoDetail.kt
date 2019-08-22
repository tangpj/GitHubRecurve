package com.tangpj.repository.entry.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters
import com.tangpj.repository.valueObject.Owner
import kotlinx.android.parcel.Parcelize

@Parcelize
@TypeConverters(StringListTypeConverters::class)
@Entity(
        indices = [Index("id")],
        primaryKeys = ["owner_login","name"]
)
data class RepoDetail constructor(
        val id: String,
        @Embedded(prefix = "owner_")
        val owner: Owner,
        val name: String,
        val description: String,
        val stars: Int,
        val forks: Int,
        val watchers: Int,
        val url: String,
        val sshUrl: String,
        val topics: List<String> = emptyList()

) : Parcelable