/*
 * Copyright (C) 2018 Tang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tang.com.recurve.base

import android.databinding.ViewDataBinding
import android.support.annotation.IntDef
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup


/**
 * Created by tang on 2018/3/10.
 */
abstract class ModulesAdapter
    : RecyclerView.Adapter<ModulesAdapter.ModulesViewHolder>() {

    private var creatorList: MutableList<ICreator<*,*>>
            = mutableListOf()


    fun setCreator(creatorList: MutableList<ICreator<*,*>>){
        val creatorMap = creatorList.groupBy { it.getItemViewType() }
        for (entry in creatorMap) {
            if (entry.value.size > 1){
                throw IllegalArgumentException("Creator ItemViewType can't not equal")
            }
        }
        this.creatorList = creatorList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesAdapter.ModulesViewHolder{
        val viewTypeList = creatorList.groupBy { it.getItemViewType() }[viewType]
        return viewTypeList?.first()?.onCreateHeaderHolder()
                ?: creatorList.first().onCreateHeaderHolder()
    }

    override fun onBindViewHolder(holder: ModulesAdapter.ModulesViewHolder, position: Int) {

    }

    fun <E,ItemHolder: RecyclerView.ViewHolder>
            notifyModulesItemChange(creator: ICreator<E,ItemHolder>,aimsPositon: Int){
        val creatorPosition = creatorList.indexOf(creator)
        var startPosition: Int = 0
        creatorList.forEachIndexed { index, iCreator ->
            if( index == creatorPosition) return@forEachIndexed
            else startPosition += iCreator.getItemCount()
        }

        notifyItemRangeChanged(aimsPositon,creator.getItemCount())
    }

    override fun getItemCount(): Int  = creatorList.sumBy { it -> it.getItemCount() }

    override fun getItemViewType(position: Int): Int {
        var sum = 0
        creatorList.forEach {
            sum += it.getItemCount()
            if (sum > position)
                return@getItemViewType it.getItemViewType()
        }
        return -1
    }

    abstract class ModulesViewHolder (binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root){
//        abstract fun setData(e: E)
    }
}


