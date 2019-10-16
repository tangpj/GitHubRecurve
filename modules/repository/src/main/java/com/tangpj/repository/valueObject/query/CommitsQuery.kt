package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import com.tangpj.repository.ApolloCommitsQuery
import com.tangpj.repository.entity.domain.author.CommitAuthor
import com.tangpj.repository.mapper.getApolloAuthor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommitsQuery(
        val gitObjectQuery: GitObjectQuery,
        val author: CommitAuthor? = null) : Parcelable{
}

fun CommitsQuery.getApolloCommitsQuery(
        startFirst: Int = 10,
        after: String? = null
) : ApolloCommitsQuery = ApolloCommitsQuery
        .builder()
        .startFirst(startFirst)
        .after(after)
        .login(gitObjectQuery.repoDetailQuery.login)
        .repoName(gitObjectQuery.repoDetailQuery.name)
        .expression(gitObjectQuery.getExpression())
        .author(author?.getApolloAuthor())
        .build()

fun ApolloCommitsQuery.startFirst() =
        variables().startFirst().value

fun ApolloCommitsQuery.after() =
        variables().after().value
