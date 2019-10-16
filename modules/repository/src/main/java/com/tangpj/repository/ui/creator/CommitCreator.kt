package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.recurve.paging.PagedItemCreator
import com.tangpj.github.ui.creator.BaseDiffUtil
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ItemCommitBinding
import com.tangpj.repository.vo.CommitVo
import com.tangpj.res.avatarHolder
import javax.inject.Inject

class CommitCreator @Inject constructor(
        diffUtil: CommitDiffUtil)
    : PagedItemCreator<CommitVo, ItemCommitBinding>(0, diffUtil){
    override fun onBindItemView(binding: ItemCommitBinding, e: CommitVo, inCreatorPosition: Int) {
        binding.commit = e
        binding.avatarPlaceholder = avatarHolder()
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemCommitBinding{
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_commit,
                parent,
                false)
    }
}

class CommitDiffUtil @Inject constructor() : BaseDiffUtil<CommitVo>() {
    override fun areItemsTheSame(oldItem: CommitVo, newItem: CommitVo): Boolean =
            oldItem.commit.id == newItem.commit.id
}