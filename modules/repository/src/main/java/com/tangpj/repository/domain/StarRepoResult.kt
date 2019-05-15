package com.tangpj.repository.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index

@Entity(primaryKeys = ["login","repoId"],
        indices = [
                Index("login")])
data class StarRepoResult @JvmOverloads @Ignore constructor(
        val login: String,
        val repoIds: String,
        @Embedded(prefix = "star_")
        val pageInfo: PageInfo,
        val starredAt: Long = 0)

