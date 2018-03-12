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

import android.support.v7.widget.RecyclerView

/**
 * Created by tang on 2018/3/11.
 */
abstract class ItemCreator<E,in ItemHolder: RecyclerView.ViewHolder> (
        private val adapter: ModulesAdapter): Creator<E,ItemHolder>{

    private var dataList: MutableList<E> = mutableListOf()

    override fun setDataList(dataList: MutableList<E>){
        this.dataList = dataList
        if (dataList.size > 0){
            adapter.notifyModulesItemSetChange(this)
        }
    }

    final override fun getData(position: Int): E = dataList[position]

    final override fun addItem(e: E): Boolean{
        val isSucceed = dataList.add(e)
        return if (isSucceed){
            adapter.notifyModulesItemInserted(this,dataList.size - 1)
            true
        }else {
            false
        }
    }

    final override fun setItem(position: Int, e: E): E? {
        val result = dataList.set(position,e)
        if (result != null){
            adapter.notifyModulesItemChanged(this,position)
        }
        return result
    }

    final override fun removedItem(e: E): Boolean {
        val removedPosition = dataList.indexOf(e)
        val isSucceed = dataList.remove(e)
        return if (isSucceed){
            adapter.notifyModulesItemRemoved(this,removedPosition)
            true
        }else{
            false
        }
    }

    final override fun removedItemAt(position: Int): E?{
        val removedItem = dataList.removeAt(position)
        if (removedItem != null){
            adapter.notifyModulesItemRemoved(this,position)
        }
        return removedItem
    }

    final override fun getItemCount() = dataList.size

    override fun getItemViewType(): Int = 0

    final override fun onBindItemView(itemHolder: RecyclerView.ViewHolder, inCreatorPosition: Int) {
        onBindItemView(itemHolder,dataList[inCreatorPosition],inCreatorPosition)
    }



}