package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters
import kotlinx.android.parcel.Parcelize

@Parcelize
@TypeConverters(StringListTypeConverters::class)
@Entity(
        indices = [Index("id")],
        primaryKeys = ["owner","name"]
)
class RepoDetail(
        val id: String,
        val owner: String,
        val name: String,
        val description: String,
        val stars: Int,
        val forks: Int,
        val watchers: Int,
        val url: String,
        val sshUrl: String,
        val topics: List<String>

) : Parcelable