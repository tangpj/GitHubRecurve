package com.tangpj.repository.valueObject.result

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters
import com.tangpj.github.entity.domain.PageInfo

@TypeConverters(StringListTypeConverters::class)
@Entity(primaryKeys = ["login","repoName","startFirst","after"],
        indices = [Index("login")])
data class CommitsResult(
        val login: String,
        val repoName: String,
        val authorId: String,
        val commitIds: List<String>,
        val startFirst: Int,
        val after: String,
        @Embedded(prefix = "commit_")
        val pageInfo: PageInfo? = null

)