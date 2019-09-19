package com.tangpj.repository.mapper

import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.entity.domain.actor.git.Committer
import com.tangpj.repository.entity.domain.author.CommitAuthor
import com.tangpj.repository.entity.domain.commit.Commit
import com.tangpj.repository.vo.CommitVo


fun CommitAuthor.getApolloAuthor(): com.tangpj.repository.type.CommitAuthor =
        com.tangpj.repository.type.CommitAuthor.builder()
                .id(id)
                .emails(listOf(email))
                .build()

fun  ApolloCommitsQuery.Data.getCommitHistory()  =
        (this.repository?.gitObject as? ApolloCommitsQuery.AsCommit)?.history

fun ApolloCommitsQuery.History.getLocalPageInfo() =
        this.pageInfo.fragments.pageInfoDto.mapperToLocalPageInfo()

fun ApolloCommitsQuery.History.getCommitVos() : List<CommitVo> {
    return edges?.map {
        val node  = it.node
        if (node == null){
            CommitVo()
        }else{
            val commit = Commit(
                    id =  node.abbreviatedOid,
                    message = node.message,
                    committerId = node.apolloCommitter?.user?.id ?: "",
                    committedDate = node.committedDate,
                    commentCount = node.comments.totalCount)
            val committer = Committer(
                    id = node.apolloCommitter?.user?.id ?: "",
                    name = node.apolloCommitter?.name ?: "",
                    email = node.apolloCommitter?.email ?: "",
                    avatarUrl = node.apolloCommitter?.avatarUrl ?: ""
            )
            CommitVo(commit, committer)
        }
    } ?: emptyList()
}
