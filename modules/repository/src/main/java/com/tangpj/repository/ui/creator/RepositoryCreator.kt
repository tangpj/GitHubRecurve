package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.paging.PagedItemCreator
import com.tangpj.repository.vo.Repo
import com.tangpj.repository.databinding.ItemRepositoryBinding

class RepositoryCreator constructor(
        adapter: ModulesAdapter,
        diffUtil: DiffUtil.ItemCallback<Repo>,
        creatorType: Int = 0)
    : PagedItemCreator<Repo, ItemRepositoryBinding>(adapter, creatorType, diffUtil) {

    override fun onBindItemView(itemHolder: RecurveViewHolder<ItemRepositoryBinding>,
                                e: Repo?, inCreatorPosition: Int) {
        itemHolder.binding.repo = e
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> =
            RecurveViewHolder(ItemRepositoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))

}