package com.tangpj.repository.mapper

import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.WatchRepositoriesQuery
import com.tangpj.repository.fragment.RepoDto
import org.threeten.bp.LocalDateTime

@JvmOverloads
fun StartRepositoriesQuery.Data.getRepoDtoList(
        mapListener: ((starredAt: LocalDateTime, RepoDto) -> Unit)? = null) =
        this.user?.starredRepositories?.edges?.map { edge ->
            val repoDto = edge.node.fragments.repoDto
            mapListener?.invoke(edge.starredAt, repoDto)
            repoDto
        } ?: listOf()

fun WatchRepositoriesQuery.Data.getRepoDtoList() =
        this.user?.watching?.nodes?.map {
            it.fragments.repoDto
        }