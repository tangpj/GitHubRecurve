package tang.com.recurve.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by tang on 2018/3/11.
 */
interface Creator<E,ItemHolder: RecyclerView.ViewHolder>{

    companion object {
        const val FILL = -1
        const val WRAP = -2
    }

    fun setDataList(dataList: MutableList<E>)

    fun getData(position: Int): E

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
