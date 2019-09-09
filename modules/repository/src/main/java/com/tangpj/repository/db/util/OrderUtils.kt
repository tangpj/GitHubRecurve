package com.tangpj.repository.db.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tangpj.repository.entity.BaseEntity

fun < R : BaseEntity> List<String>.loadDataOrderByMe(load: (List<String>) -> LiveData<List<R>>) : LiveData<List<R>>{
    val order = mutableMapOf<String, Int>()
    this.withIndex().forEach {
        order[it.value] = it.index
    }
    return Transformations.map(load.invoke(this)){
        it.sortedBy { r ->
            order[r.id]
        }
    }
}