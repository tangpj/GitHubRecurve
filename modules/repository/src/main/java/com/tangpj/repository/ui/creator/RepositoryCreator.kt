package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.paging.PagedItemCreator
import com.tangpj.repository.vo.RepoVo
import com.tangpj.repository.databinding.ItemRepositoryBinding

class RepositoryCreator constructor(val adapter: ModulesAdapter,diffUtil: DiffUtil.ItemCallback<RepoVo>, creatorType: Int = 0)
    : PagedItemCreator<RepoVo, ItemRepositoryBinding>(adapter, creatorType, diffUtil) {

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