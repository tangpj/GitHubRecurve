package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.recurve.binding.DataBindingAdapter
import com.tangpj.repository.databinding.ItemFilePathBinding
import com.tangpj.repository.databinding.ItemHorizontalRvBinding
import com.tangpj.repository.entry.vo.FileItem

class PathCreator : ItemCreator<FileItem, ItemHorizontalRvBinding>(){
    override fun onBindItemView(binding: ItemHorizontalRvBinding, e: FileItem, inCreatorPosition: Int) {

    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemHorizontalRvBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHorizontalRvBinding.inflate(inflater, parent, false)
    }
}

private class PathAdapter : DataBindingAdapter<FileItem, ItemFilePathBinding>(){
    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ItemFilePathBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemFilePathBinding.inflate(inflater, parent, false)
    }

    override fun onBindBinding(binding: ItemFilePathBinding, e: FileItem, position: Int) {
        binding.isRoot = position== 0
        binding.text = e.name
    }

}
