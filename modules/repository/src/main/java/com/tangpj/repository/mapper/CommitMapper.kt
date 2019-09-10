package com.tangpj.repository.mapper

import com.tangpj.github.domain.PageInfo
import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.entity.domain.author.CommitAuthor
import com.tangpj.repository.entity.domain.commit.Commit


fun CommitAuthor.getApolloAuthor(): com.tangpj.repository.type.CommitAuthor =
        com.tangpj.repository.type.CommitAuthor.builder()
                .id(id)
                .emails(listOf(email))
                .build()

fun ApolloCommitsQuery.Data.mapperToPageInfoCommitsPair() : Pair<PageInfo?,List<Commit>> {
    val history = this.repository?.gitObject as? ApolloCommitsQuery.History
    val commits = history?.edges?.map {
        val node  = it.node
        if (node == null){
            Commit("")
        }else{
            Commit(
                    id =  node.abbreviatedOid,
                    message = node.message,
                    committerId = node.apolloCommitter?.user?.id ?: "",
                    committedDate = node.committedDate,
                    commentCount = node.comments.totalCount)
        }
    } ?: emptyList()
    val pageInfo = history?.pageInfo?.fragments?.pageInfoDto?.mapperToLocalPageInfo()
    return pageInfo to commits
}
