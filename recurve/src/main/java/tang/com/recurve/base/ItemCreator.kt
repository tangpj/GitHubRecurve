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
        val adapter: ModulesAdapter): ICreator<E,ItemHolder>{

    private var dataList: MutableList<E> = mutableListOf()

    override fun setData(dataList: MutableList<E>){
        this.dataList = dataList
        if (dataList.size > 0){
            adapter.notifyModulesItemChange(this,0)
        }
    }

    override fun addData(e: E): Boolean{
        val isSucceed = dataList.add(e)
        if (isSucceed){
            adapter.notifyModulesItemChange(this,dataList.size - 1)

        }
        return false
    }

    override fun getItemCount() = dataList.size

    fun onBindHeaderHoder(holder: ItemHolder, headPosition: Int){}
}