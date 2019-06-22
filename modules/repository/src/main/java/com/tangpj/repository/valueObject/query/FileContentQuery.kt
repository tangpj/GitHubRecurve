package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import com.tangpj.repository.BlodDetailQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileContentQuery(val owner: String, val name: String, val expression: String) : Parcelable{

    fun getApolloQuery() =  BlodDetailQuery.builder()
            .owner(owner)
            .name(name)
            .expression(expression)
            .build()

}