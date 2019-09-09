package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.repository.databinding.ItemFileBinding
import com.tangpj.repository.entity.file.FileItem

class FileItemCreator
    : ItemCreator<FileItem, ItemFileBinding>(){
    override fun onBindItemView(
            binding: ItemFileBinding, e: FileItem, inCreatorPosition: Int) {
        binding.fileItem = e
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemFileBinding =
            ItemFileBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)



}