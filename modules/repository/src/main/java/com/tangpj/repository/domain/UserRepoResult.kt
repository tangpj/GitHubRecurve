package com.tangpj.repository.domain

import androidx.room.Entity
import com.tangpj.github.domain.RepoFlag

@Entity(primaryKeys = ["login"])
class UserRepoResult(
        val login: String,
        val repoIds: Int,
        val totalCount: Int,
        @RepoFlag
        var type: Int)

