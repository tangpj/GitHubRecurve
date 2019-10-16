package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recurve.paging.PagedItemCreator
import com.tangpj.github.ui.creator.EntryDiffUtil
import com.tangpj.repository.entity.domain.repo.Repo
import com.tangpj.repository.databinding.ItemRepositoryBinding
import javax.inject.Inject

class RepositoryCreator @Inject constructor(
        diffUtil: RepoDiffUtil)
    : PagedItemCreator<Repo, ItemRepositoryBinding>(0, diffUtil) {

    override fun onBindItemView(binding: ItemRepositoryBinding,
                                e: Repo, inCreatorPosition: Int) {
        binding.repo = e
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemRepositoryBinding =
            ItemRepositoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
}

class RepoDiffUtil @Inject constructor(): EntryDiffUtil<Repo>()