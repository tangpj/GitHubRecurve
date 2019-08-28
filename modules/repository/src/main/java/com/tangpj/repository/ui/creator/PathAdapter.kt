package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.recurve.binding.DataBindingAdapter
import com.tangpj.repository.databinding.ItemFilePathBinding
import com.tangpj.repository.databinding.ItemHorizontalRvBinding
import com.tangpj.repository.entry.vo.FileItem

class PathAdapter : DataBindingAdapter<PathItem, ItemFilePathBinding>(){

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ItemFilePathBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemFilePathBinding.inflate(inflater, parent, false)
    }

    override fun onBindBinding(binding: ItemFilePathBinding, e: PathItem, position: Int) {
        binding.isRoot = position== 0
        binding.text = e.name
    }

}

class PathItem(val path: String, val name: String)
