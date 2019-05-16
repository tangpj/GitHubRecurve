package com.tangpj.repository.mapper

import com.tangpj.github.domain.PageInfo
import com.tangpj.repository.StartRepositoriesQuery
import com.tangpj.repository.WatchRepositoriesQuery
import com.tangpj.repository.domain.StarRepoId
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.vo.RepoVo
import org.threeten.bp.ZoneId

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

/**
 * 把Apollo框架生成的PageInfo转换成本地的[PageInfo]
 *
 * @method: mapperToLocalPageInfo
 * @author create by Tang
 * @date 2019-05-15 21:53
 */
fun StartRepositoriesQuery.PageInfo.mapperToLocalPageInfo() = PageInfo(
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
fun StartRepositoriesQuery.Data.mapperToRepoVoList(
        starRepoResultListener: ((starRepoResult: StarRepoResult) -> Unit)? = null) : List<RepoVo>{
    val edges = this.user?.starredRepositories?.edges
    edges?.size ?: return mutableListOf()
    val ids = ArrayList<String>(edges.size)
    val repoVoList = edges.map { edge ->
        val repoDto = edge.node.fragments.repoDto
        starRepoResultListener?.let {
            ids.add(repoDto.id)
        }
        repoDto.mapperToRepoVo()
    }

    val pageInfo =  user?.starredRepositories?.pageInfo
    if (ids.size > 0 && starRepoResultListener != null &&  pageInfo != null){
        val starRepoResult = StarRepoResult(
                login = this.user?.login ?: "",
                repoIds = ids,
                pageInfo = pageInfo.mapperToLocalPageInfo())
        starRepoResultListener.invoke(starRepoResult)
    }

    return repoVoList

}

fun WatchRepositoriesQuery.Data.getRepoDtoList() =
        this.user?.watching?.nodes?.map {
            it.fragments.repoDto
        }