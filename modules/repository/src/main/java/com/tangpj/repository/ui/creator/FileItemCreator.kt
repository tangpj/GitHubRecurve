package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.repository.databinding.ItemFileBinding
import com.tangpj.repository.vo.FileItem

class FileItemCreator constructor(adapter: ModulesAdapter)
    : ItemCreator<FileItem, ItemFileBinding>(adapter){
    override fun onBindItemView(
            itemHolder: RecurveViewHolder<ItemFileBinding>, e: FileItem?, inCreatorPosition: Int) {
        itemHolder.binding.fileItem = e
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> =
            RecurveViewHolder(ItemFileBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))



}