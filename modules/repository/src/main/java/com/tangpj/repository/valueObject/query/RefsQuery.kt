package com.tangpj.repository.valueObject.query

import androidx.lifecycle.LiveData
import com.tangpj.github.valueObject.Query
import com.tangpj.repository.ApolloRefsQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefsQuery(
        val repoDetailQuery: RepoDetailQuery,
        val prefix: Prefix)
    : Query<RefsQuery>{
    override fun <R> ifExists(f: (RefsQuery) -> LiveData<R>): LiveData<R> =
            repoDetailQuery.ifExists {
                f(this)
            }
}

fun RefsQuery.getApolloRefsQuery(
        startFirst: Int = 10,
        after: String? = null) =
        ApolloRefsQuery.builder()
                .startFirst(startFirst)
                .after(after)
                .login(repoDetailQuery.login)
                .name(repoDetailQuery.name)
                .refPrefix(prefix.refName)
                .build()

enum class Prefix(val refName: String) {
    HEAD("refs/heads/"),
    TAGS("refs/tags/");
}