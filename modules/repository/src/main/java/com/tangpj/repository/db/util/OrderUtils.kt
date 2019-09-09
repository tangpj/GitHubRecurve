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

/**
 *
 *
 * @method: loadLisCovertToMapForMe
 * @author create by Tang
 * @date 2019-09-09 23:52
 */
fun <R : BaseEntity> List<String>.loadLisCovertToMapForMe(load: (List<String>) -> LiveData<List<R>>) : LiveData<Map<String, R>>{
    val order = mutableMapOf<String, Int>()
    this.withIndex().forEach {
        order[it.value] = it.index
    }
    return Transformations.map(load.invoke(this)){
        it.mapIndexed { index, r ->  }
    }
}