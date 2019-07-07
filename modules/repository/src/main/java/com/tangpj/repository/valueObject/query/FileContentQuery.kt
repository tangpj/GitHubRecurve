package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.tangpj.github.utils.AbsentLiveData
import com.tangpj.repository.BlodDetailQuery
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileContentQuery(
        val repoDetailQuery: RepoDetailQuery, val expression: String) : Parcelable{

    fun <T> ifExists(f: (FileContentQuery) -> LiveData<T>) =
            if (repoDetailQuery.owner.isBlank()
                    || repoDetailQuery.name.isBlank()
                    || expression.isBlank()){
                AbsentLiveData.create()
            }else{
                f.invoke(this)
            }

    fun getApolloQuery() : BlodDetailQuery =  BlodDetailQuery.builder()
            .owner(repoDetailQuery.owner)
            .name(repoDetailQuery.name)
            .expression(expression)
            .build()

}