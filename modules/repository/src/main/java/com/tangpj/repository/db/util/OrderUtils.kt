package com.tangpj.repository.db.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tangpj.repository.entity.BaseEntity

fun < R : BaseEntity> List<String>.loadDataOrderById(load: (List<String>) -> LiveData<List<R>>) : LiveData<List<R>>{
    val order = mutableMapOf<String, Int>()
    this.withIndex().forEach {
        order[it.value] = it.index
    }
    return Transformations.map(load(this)){
        it.sortedBy { r ->
            order[r.id]
        }
    }
}

/**
 *  根据ids加载目标队列，并把目标队列转换成key为id，value为[R]的Map
 *
 * @method: loadLisCovertToMapForMe
 * @author create by Tang
 * @date 2019-09-09 23:52
 */
fun <R : BaseEntity> List<String>.loadDataCovertMapById(load: (List<String>) -> LiveData<List<R>>) =
        Transformations.map(load(this)){ list ->
            list.groupingBy{ it.id }
                    .aggregate { _, accumulator: R?, element, isFirst ->
                        if (isFirst){
                            element
                        }else {
                            accumulator ?: element
                        }
                    }
        }
