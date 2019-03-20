package com.tangpj.repository.mapper

import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.WatchRepositoriesQuery

fun StartRepositoriesQuery.Data.getRepoDtoList() =
        this.user?.starredRepositories?.edges?.map {
            it.node.fragments.repoDto
        }

fun WatchRepositoriesQuery.Data.getRepoDtoList() =
        this.user?.watching?.nodes?.map {
            it.fragments.repoDto
        }