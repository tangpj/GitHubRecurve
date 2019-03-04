package com.tangpj.repository.domain

import androidx.room.Entity
import com.tangpj.github.domain.RepoFlag

@Entity(primaryKeys = ["login"])
class UserRepoResult(
        val login: String,
        val repoIds: List<Int>,
        val totalCount:Int,
        val type: Int = RepoFlag.NORMAL
)

