package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.paging.PagedItemCreator
import com.tangpj.repository.entity.domain.repo.Repo
import com.tangpj.repository.databinding.ItemRepositoryBinding

class RepositoryCreator constructor(
        diffUtil: DiffUtil.ItemCallback<Repo>,
        creatorType: Int = 0)
    : PagedItemCreator<Repo, ItemRepositoryBinding>(creatorType, diffUtil) {

    override fun onBindItemView(binding: ItemRepositoryBinding,
                                e: Repo, inCreatorPosition: Int) {
        binding.repo = e
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemRepositoryBinding =
            ItemRepositoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

}