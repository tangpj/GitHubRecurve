package com.tangpj.repository.valueObject.result

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters
import com.tangpj.github.entity.domain.PageInfo
import com.tangpj.repository.db.typeConverters.PrefixTypeConverter
import com.tangpj.repository.valueObject.query.Prefix

@TypeConverters(value = [StringListTypeConverters::class, PrefixTypeConverter::class])
@Entity(primaryKeys = ["login", "repoName", "prefix", "startFirst", "after"])
data class RefsResult(
        val login: String,
        val repoName: String,
        val prefix: Prefix,
        val refsIds: List<String>,
        val startFirst: Int,
        val after: String,
        @Embedded(prefix = "refs_")
        val pageInfo: PageInfo? = null

)