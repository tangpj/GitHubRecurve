package com.tangpj.repository.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.repository.vo.RepoVo
import com.tangpj.repository.databinding.ItemRepositoryBinding

class RepositoryCreator constructor(adapter: ModulesAdapter, creatorType: Int)
    : ItemCreator<RepoVo, ItemRepositoryBinding>(adapter, creatorType) {

    constructor(adapter: ModulesAdapter): this(adapter, 0)

    override fun onBindItemView(itemHolder: RecurveViewHolder<ItemRepositoryBinding>?,
                                e: RepoVo?, inCreatorPosition: Int) {
        itemHolder?.let {
            it.binding.repoVo = e
        }
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> =
            RecurveViewHolder(
                    ItemRepositoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))

}