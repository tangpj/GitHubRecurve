package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import com.tangpj.repository.BlodDetailQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileContentQuery(
        val repoDetailQuery: RepoDetailQuery, val expression: String) : Parcelable{

    fun getApolloQuery() =  BlodDetailQuery.builder()
            .owner(repoDetailQuery.owner)
            .name(repoDetailQuery.name)
            .expression(expression)
            .build()

}