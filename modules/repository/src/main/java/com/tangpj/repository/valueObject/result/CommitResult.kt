package com.tangpj.repository.valueObject.result

import androidx.room.Embedded
import com.tangpj.github.domain.PageInfo
import com.tangpj.repository.type.CommitAuthor

data class CommitResult(
        val owner: String,
        val startFirst: Int,
        val after: String,
        val commitIds: List<String>,
        @Embedded(prefix = "_author")
        val author: CommitAuthor? = null,
        @Embedded(prefix = "commit_")
        val pagerInfo: PageInfo? = null

)