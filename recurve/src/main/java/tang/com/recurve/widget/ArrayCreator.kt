package tang.com.recurve.widget

import android.databinding.ViewDataBinding
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import tang.com.recurve.R

/**
 * Created by tang on 2018/3/14.
 * 快速构建简单的队列Creator
 * @param T 数据实体类
 * @param adapter
 * @param layoutId
 * @param viewId
 * @param stringConverter 字符串转换器
 * @param itemType adapter中Item的类型
 * 当T的toString不满足需求时可以通过转换器来定义转换规则
 */
class ArrayCreator<T>(adapter: ModulesAdapter
                      , @LayoutRes private val layoutId: Int = R.layout.item_text
                      , @IdRes private val viewId: Int = R.id.text1
                      , itemType: Int = 0
                      , private val stringConverter: ((T) -> String)? = null)
    : ItemCreator<T, ArrayCreator.ArrayViewHolder>(adapter,itemType) {
    override fun onCreateItemView(parent: ViewGroup): ArrayViewHolder {
        return ArrayViewHolder(LayoutInflater.from(parent.context).inflate(layoutId,parent,false),viewId)
    }

    override fun onBindItemView(itemHolder: ArrayViewHolder, e: T, inCreatorPosition: Int) {
        itemHolder.textView.text = stringConverter?.invoke(e) ?: e.toString()
    }

    class ArrayViewHolder(itemView: View, @IdRes private val viewId: Int): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(viewId)
    }
}

fun stringCreator(adapter: ModulesAdapter,itemType: Int = 0): ArrayCreator<String>{
    return ArrayCreator(adapter, itemType = itemType)
}