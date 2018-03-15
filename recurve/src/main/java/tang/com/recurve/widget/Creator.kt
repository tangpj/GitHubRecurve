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
package tang.com.recurve.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by tang on 2018/3/11.
 */
interface Creator<E,ItemHolder: RecyclerView.ViewHolder>{

    fun setDataList(dataList: MutableList<E>)

    fun getData(): List<E>

    fun getItem(position: Int): E

    fun addItem(e: E): Boolean

    fun setItem(position: Int,e: E): E?

    fun removedItem(e: E): Boolean

    fun removedItemAt(position: Int):E?

    fun getItemCount(): Int

    fun getItemViewType(): Int

    fun getSpan(): Int

    fun onCreateItemView(parent: ViewGroup): ItemHolder

    fun onBindItemView(itemHolder: RecyclerView.ViewHolder, inCreatorPosition: Int)

    fun onBindItemView(itemHolder: ItemHolder, e: E, inCreatorPosition: Int)
}
