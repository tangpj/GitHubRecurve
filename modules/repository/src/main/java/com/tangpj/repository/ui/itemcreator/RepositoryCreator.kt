package com.tangpj.repository.ui.itemcreator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.recurve.recyclerview.adapter.ModulesAdapter
import com.tangpj.recurve.recyclerview.creator.ItemCreator
import com.tangpj.recurve.recyclerview.creator.RecurveViewHolder
import com.tangpj.repository.databinding.ItemRepositoryBinding
import com.tangpj.repository.vo.Repository

class RepositoryCreator(adapter: ModulesAdapter, creatorType: Int)
    : ItemCreator<Repository, ItemRepositoryBinding>(adapter, creatorType) {
    override fun onBindItemView(itemHolder: RecurveViewHolder<ItemRepositoryBinding>?,
                                e: Repository?, inCreatorPosition: Int) {
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> =
            RecurveViewHolder(
                    ItemRepositoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))


}