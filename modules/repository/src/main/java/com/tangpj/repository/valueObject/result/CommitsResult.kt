package com.tangpj.repository.valueObject.result

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters
import com.tangpj.github.domain.PageInfo
import com.tangpj.repository.entity.author.CommitAuthor

@TypeConverters(StringListTypeConverters::class)
@Entity(primaryKeys = ["login","name","startFirst","after"],
        indices = [Index("login")])
data class CommitsResult(
        val login: String,
        val name: String,
        val authorId: String,
        val commitIds: List<String>,
        val startFirst: Int,
        val after: String,
        @Embedded(prefix = "commit_")
        val pageInfo: PageInfo? = null

)