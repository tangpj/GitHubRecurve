package com.tangpj.repository.mapper

import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.entity.author.CommitAuthor
import com.tangpj.repository.entity.commit.Commit


fun CommitAuthor.getApolloAuthor(): com.tangpj.repository.type.CommitAuthor =
        com.tangpj.repository.type.CommitAuthor.builder()
                .id(id)
                .emails(listOf(email))
                .build()

fun ApolloCommitsQuery.Data.mapperToCommits() : List<Commit> {
    val history = this.repository?.gitObject as? ApolloCommitsQuery.History
    return history?.edges?.map {
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
}
