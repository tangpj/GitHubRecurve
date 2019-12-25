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

    var selectBranch: String? = null
    set(value) {
        field = value
        val pageList = getPageList()
        pageList ?: return
        val ref = pageList.first{ ref -> value == ref.name }
        mAdapter.notifyModulesItemChanged(this, pageList.indexOf(ref))
    }

    override fun onBindItemView(binding: ItemRefBinding, e: Ref, inCreatorPosition: Int) {
        binding.name = e.name
        binding.isCheck = e.name == selectBranch
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