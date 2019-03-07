package com.tangpj.repository.domain

import androidx.room.Entity
import com.tangpj.github.domain.RepoFlag
import com.tangpj.github.domain.RepoFlag.QUERY

@Entity(primaryKeys = ["login"])
class UserRepoResult(
        val login: String,
        val repoId: Int,
        val totalCount: Int,
        @RepoFlag
        var type: Int = QUERY)

