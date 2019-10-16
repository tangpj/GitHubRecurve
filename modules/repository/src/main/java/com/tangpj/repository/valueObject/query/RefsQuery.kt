package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import com.tangpj.repository.ApolloRefsQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefsQuery(
        val repoDetailQuery: RepoDetailQuery,
        val prefix: Prefix)
    : Parcelable

fun RefsQuery.getApolloRefsQuery(
        startFirst: Int = 10,
        after: String? = null): ApolloRefsQuery =
        ApolloRefsQuery.builder()
                .startFirst(startFirst)
                .after(after)
                .login(repoDetailQuery.login)
                .name(repoDetailQuery.name)
                .refPrefix(prefix.refName)
                .build()

@Parcelize
enum class Prefix(val refName: String) : Parcelable{
    HEAD("refs/heads/"),
    TAGS("refs/tags/");

    companion object{
        fun getPrefixRefName(refName: String) =
                when(refName){
                    "refs/heads/" -> HEAD
                    "refs/tags/" -> TAGS
                    else -> HEAD
                }
    }
}

