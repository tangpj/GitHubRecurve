package tang.com.recurve.base

import android.support.v7.widget.RecyclerView

/**
 * Created by tang on 2018/3/11.
 */
interface ICreator<E,in HeaderHolder: RecyclerView.ViewHolder>{
    fun setData(dataList: MutableList<E>)
    fun addData(e: E): Boolean
    fun getItemCount(): Int
    fun getItemViewType(): Int
    fun onCreateHeaderHolder(): ModulesAdapter.ModulesViewHolder

}