package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.recurve.binding.adapter.DataBindingAdapter
import com.tangpj.repository.databinding.ItemFilePathBinding

class PathAdapter : DataBindingAdapter<PathItem, ItemFilePathBinding>(){

    val pathPosition = mutableMapOf<PathItem, Int>()

    /**
     *
     * if [pathItem] is contains，delete the node behind [pathItem]
     *
     * @method: pushPathItem
     * @author: tang
     * @createTime: 2019-08-29 16:23
     */
    fun pushPathItem(pathItem: PathItem){
        if (pathPosition.containsKey(pathItem)){
            pathPosition[pathItem]?.let { it ->
                val start = it + 1
                removeItemRange(start, itemCount - start){ path ->
                    pathPosition.remove(path)
                }
            }
        }else{
            pathPosition[pathItem] = itemCount
            addItem(pathItem)
        }
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ItemFilePathBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemFilePathBinding.inflate(inflater, parent, false)
    }

    override fun onBindBinding(binding: ItemFilePathBinding, e: PathItem, position: Int) {
        binding.isRoot = position== 0
        binding.text = e.name
    }

}

data class PathItem(val path: String, val name: String)
