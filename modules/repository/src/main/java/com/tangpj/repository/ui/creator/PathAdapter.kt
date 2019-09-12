package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.binding.adapter.DataBindingAdapter
import com.tangpj.repository.databinding.ItemFilePathBinding



class PathAdapter : DataBindingAdapter<PathItem, ItemFilePathBinding>(){

    private val pathPosition = mutableMapOf<PathItem, Int>()

    private lateinit var internalRecyclerView: RecyclerView

    /**
     *
     * if [pathItem] is contains，delete the node behind [pathItem]
     *
     * @method: pushPathItem
     * @author: tang
     * @createTime: 2019-08-29 16:23
     */
    fun pushPathItem(pathItem: PathItem){
        val count = itemCount
        if (count - 1 == pathPosition[pathItem]){
            return
        }
        if (pathPosition.containsKey(pathItem)){
            pathPosition[pathItem]?.let { it ->
                val start = it + 1
                removeItemRange(start, count - start){ path ->
                    pathPosition.remove(path)
                }
            }
        }else {
            pathPosition[pathItem] = count
            addItem(pathItem)
            internalRecyclerView.scrollToPosition(itemCount - 1)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)


        internalRecyclerView = recyclerView
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ItemFilePathBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemFilePathBinding.inflate(inflater, parent, false)
    }

    override fun onBindBinding(binding: ItemFilePathBinding, e: PathItem, position: Int) {
        binding.isRoot = position == 0
        binding.text = e.name
    }

}

data class PathItem(val path: String, val name: String)
