package com.tangpj.repository.mapper

import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.WatchRepositoriesQuery
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.vo.RepoVo
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.lang.StringBuilder

fun RepoDto.mapperToRepoVo(): RepoVo{
    val languages = languages?.nodes
    val languageDto = if (languages != null && languages.size > 0){
        languages[0].fragments.languageDto
    }else{
        null
    }
    return RepoVo(
            id = id,
            name = name,
            fullName = "${owner.login}/$name",
            language = languageDto?.name ?: "unknown",
            languageColor = languageDto?.color ?: "unknown",
            description = description ?: "",
            stars = stargazers.totalCount,
            forks = forks.totalCount)
}

fun StartRepositoriesQuery.Data.mapperToRepoVoList(
        starRepoResultListener: ((starRepoResult: StarRepoResult) -> Unit)? = null) : List<RepoVo>{
    val edges = this.user?.starredRepositories?.edges
    edges?.size ?: return mutableListOf()
    val ids = ArrayList<String>(edges.size)
    val repoVoList = edges.map {
        val repoDto = it.node.fragments.repoDto
        starRepoResultListener?.let {
            ids.add(repoDto.id)
        }
        repoDto.mapperToRepoVo()
    }
    val zoneId = ZoneId.systemDefault()
    StarRepoResult(
            login,
            repoDto.id,
            starredAt.atZone(zoneId).toInstant().toEpochMilli()))
    return repoVoList

}

fun WatchRepositoriesQuery.Data.getRepoDtoList() =
        this.user?.watching?.nodes?.map {
            it.fragments.repoDto
        }