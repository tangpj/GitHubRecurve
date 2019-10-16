package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import android.text.TextUtils
import androidx.lifecycle.LiveData
import com.tangpj.github.utils.AbsentLiveData
import com.tangpj.repository.ApolloBlobDetailQuery
import com.tangpj.repository.ApolloFileTreeQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitObjectQuery(
        val repoDetailQuery: RepoDetailQuery, val branch: String, val path: String = "") : Parcelable{
    fun nextPath(fileName: String) = if(TextUtils.isEmpty(path) || path == ":"){
        ":$fileName"
    }else{
        "$path/$fileName"
    }
}

fun GitObjectQuery.getExpression() :String{
    return when{
        branch.isBlank() && path.isBlank() -> ""
        branch.isNotBlank() && path.isBlank() -> branch
        else -> "$branch$path"
    }
}

fun GitObjectQuery.getApolloFileTreeQuery(): ApolloFileTreeQuery = ApolloFileTreeQuery.builder()
        .owner(repoDetailQuery.login)
        .name(repoDetailQuery.name)
        .expression(getExpression())
        .build()

fun GitObjectQuery.getApolloBlobQuery(): ApolloBlobDetailQuery =  ApolloBlobDetailQuery.builder()
        .owner(repoDetailQuery.login)
        .name(repoDetailQuery.name)
        .expression(getExpression())
        .build()
