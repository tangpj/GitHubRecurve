package tang.com.recurve.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by tang on 2018/3/11.
 */
interface Creator<E,in HeaderHolder: RecyclerView.ViewHolder>{

    fun setDataList(dataList: MutableList<E>)

    fun getData(position: Int): E

    fun addItem(e: E): Boolean

    fun setItem(position: Int,e: E): E?

    fun removedItem(e: E): Boolean

    fun removedItemAt(position: Int):E?

    fun getItemCount(): Int

    fun getItemViewType(): Int

    fun onCreateItemView(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindItemView(itemHolder: RecyclerView.ViewHolder, inCreatorPosition: Int)

    fun onBindItemView(itemHolder: RecyclerView.ViewHolder, e: E, inCreatorPosition: Int)
}