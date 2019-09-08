package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.tangpj.github.utils.AbsentLiveData
import com.tangpj.github.valueObject.Query
import com.tangpj.repository.ApolloRepoDetailQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoDetailQuery(val login: String, val name: String)
    : Query<RepoDetailQuery>, Parcelable {
    override fun <T> ifExists(f: (RepoDetailQuery) -> LiveData<T>): LiveData<T> {
        return if (login.isBlank() || name.isBlank()){
            AbsentLiveData.create()
        }else{
            f.invoke(this)
        }
    }
}

fun RepoDetailQuery.getApolloRepoDetailQuery(): ApolloRepoDetailQuery = ApolloRepoDetailQuery.builder()
        .owner(login)
        .name(name)
        .build()