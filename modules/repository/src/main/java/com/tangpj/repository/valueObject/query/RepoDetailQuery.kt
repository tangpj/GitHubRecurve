package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import com.tangpj.repository.ApolloRepoDetailQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoDetailQuery(val login: String, val name: String) : Parcelable

fun RepoDetailQuery.getApolloRepoDetailQuery(): ApolloRepoDetailQuery = ApolloRepoDetailQuery.builder()
        .owner(login)
        .name(name)
        .build()