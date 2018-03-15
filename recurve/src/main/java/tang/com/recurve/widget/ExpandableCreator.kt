package tang.com.recurve.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by tang on 2018/3/15.
 */

interface ExpandableCreator<Parent,Child,ItemHolder: RecyclerView.ViewHolder>{

    fun setDataList(dataList: LinkedHashMap<Parent,MutableList<Child>>)

    fun getData(): LinkedHashMap<Parent,List<Child>>

    fun addParentItem(parent: Parent): Boolean

    fun setParentItem(parentPosition: Int, parent: Parent): Parent?

    fun removedParentItem(parent: Parent): Boolean

    fun removedItemAt(position: Int):E?

    fun getItemCount(): Int

    fun getItemViewType(): Int

    fun getSpan(): Int

    fun onCreateItemView(parent: ViewGroup): ItemHolder

    fun onBindItemView(itemHolder: RecyclerView.ViewHolder, inCreatorPosition: Int)

    fun onBindItemView(itemHolder: ItemHolder, e: E, inCreatorPosition: Int)
}