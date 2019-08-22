package com.tangpj.repository.mapper

import com.tangpj.github.domain.PageInfo
import com.tangpj.repository.ApolloStartRepositoriesQuery
import com.tangpj.repository.ApolloWatchRepositoriesQuery
import com.tangpj.repository.fragment.OwnerDto
import com.tangpj.repository.valueObject.result.StarRepoResult
import com.tangpj.repository.fragment.PageInfoDto
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.valueObject.Owner
import com.tangpj.repository.entry.vo.Repo

fun RepoDto.mapperToRepo(): Repo{
    val languageDto = primaryLanguage?.fragments?.languageDto
    val ownerDto = owner.fragments.ownerDto
    val localOwner = ownerDto.getOwner()
    return Repo(
            id = id,
            name = name,
            owner = localOwner,
            fullName = "${ownerDto.login}/$name",
            language = languageDto?.name ?: "unknown",
            languageColor = languageDto?.color ?: "unknown",
            description = description ?: "",
            stars = stargazers.totalCount,
            forks = forks.totalCount)
}

fun OwnerDto.getOwner() = Owner(id, login, avatarUrl)

/**
 * 把Apollo框架生成的PageInfo转换成本地的[PageInfo]
 *
 * @method: mapperToLocalPageInfo
 * @author create by Tang
 * @date 2019-05-15 21:53
 */
fun PageInfoDto.mapperToLocalPageInfo() = PageInfo(
        hasNextPage = isHasNextPage,
        hasPreviousPage = isHasPreviousPage,
        startCursor = startCursor ?: "",
        endCursor = endCursor ?: "")


/**
 * [starRepoResultListener] 如果不为空，则生成并回调[StarRepoResult]的值对象
 * 这样设计的目的是减少数组遍历，提高性能
 *
 * @method: mapperToRepoVoList
 * @author create by Tang
 * @date 2019-05-15 21:47
 *
 */
fun ApolloStartRepositoriesQuery.Data.mapperToRepoVoList() : List<Repo>{
    val edges = this.user?.starredRepositories?.edges
    edges?.size ?: return mutableListOf()
    val repoVoList = edges.map { edge ->
        val repoDto = edge.node.fragments.repoDto
        repoDto.mapperToRepo()
    }
    return repoVoList

}

fun ApolloStartRepositoriesQuery.Data.getPageInfo() : PageInfo? =
        user?.starredRepositories?.pageInfo?.fragments?.pageInfoDto?.mapperToLocalPageInfo()

fun ApolloWatchRepositoriesQuery.Data.getRepoDtoList() =
        this.user?.watching?.nodes?.map {
            it.fragments.repoDto
        }