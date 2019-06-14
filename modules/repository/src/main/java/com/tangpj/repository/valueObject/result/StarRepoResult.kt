package com.tangpj.repository.valueObject.result

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters
import com.tangpj.github.domain.PageInfo

@Entity(primaryKeys = ["login"],
        indices = [
                Index("login")])

@TypeConverters(StringListTypeConverters::class)
data class StarRepoResult constructor(
        val login: String,
        val repoIds: List<String>,
        @Embedded(prefix = "star_")
        val pageInfo: PageInfo?)


@Entity(primaryKeys = ["id"])
data class StarRepoId(
        val id: String,
        val starredAt: Long)

