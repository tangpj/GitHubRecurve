package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.tangpj.github.utils.AbsentLiveData
import com.tangpj.repository.BlobDetailQuery
import com.tangpj.repository.FileTreeQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitObjectQuery(
        val repoDetailQuery: RepoDetailQuery, val branch: String, val path: String = "") : Parcelable{

    fun <T> ifExists(f: (GitObjectQuery) -> LiveData<T>) =
            if (repoDetailQuery.owner.isBlank()
                    || repoDetailQuery.name.isBlank()
                    || getExpression().isBlank()){
                AbsentLiveData.create()
            }else{
                f.invoke(this)
            }


    fun getExpression() :String{
        return when{
            branch.isBlank() && path.isBlank() -> ""
            branch.isNotBlank() && path.isBlank() -> "$branch:"
            else -> "$branch:$path"
        }
    }
}

fun GitObjectQuery.getApolloFileTreeQuery() : FileTreeQuery = FileTreeQuery.builder()
        .owner(repoDetailQuery.owner)
        .name(repoDetailQuery.name)
        .expression(getExpression())
        .build()

fun GitObjectQuery.getApolloBlobQuery() : BlobDetailQuery =  BlobDetailQuery.builder()
        .owner(repoDetailQuery.owner)
        .name(repoDetailQuery.name)
        .expression(getExpression())
        .build()
