package com.tangpj.repository.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.recurve.paging.PagedItemCreator
import com.tangpj.github.ui.creator.BaseDiffUtil
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ItemRefBinding
import com.tangpj.repository.entity.domain.Ref
import javax.inject.Inject


class RefCreator @Inject constructor(
        diffUtil: RefDiffUtil) : PagedItemCreator<Ref, ItemRefBinding>(0, diffUtil){

    override fun onBindItemView(binding: ItemRefBinding, e: Ref, inCreatorPosition: Int) {
        binding.name = e.name
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemRefBinding =
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_ref,
                    parent,
                    false
            )

}


class RefDiffUtil @Inject constructor() : BaseDiffUtil<Ref>() {
    override fun areItemsTheSame(oldItem: Ref, newItem: Ref): Boolean =
        oldItem.id == newItem.id
}