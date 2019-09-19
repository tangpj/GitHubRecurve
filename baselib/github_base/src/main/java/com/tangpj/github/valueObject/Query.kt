package com.tangpj.github.valueObject

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.tangpj.github.utils.AbsentLiveData

interface Query<Child> : Parcelable {

    fun <R> ifExists(f: (Child) -> LiveData<R>) : LiveData<R> = AbsentLiveData.create()

    fun <R, T1, T2> ifExistsForQuery2(
            query1: Query<T1>,
            query2: Query<T2>,
            f: (T1, T2) -> LiveData<R>) : LiveData<R>{
        return query1.ifExists { t1 ->
           query2.ifExists { t2 ->
               f(t1, t2)
           }
        }
    }
}