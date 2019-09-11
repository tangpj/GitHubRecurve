package com.tangpj.github.ui.creator

import com.tangpj.github.entity.BaseEntity

open class EntryDiffUtil <E : BaseEntity> : BaseDiffUtil<E>() {

    override fun areItemsTheSame(oldItem: E, newItem: E): Boolean =
            oldItem.id == newItem.id

}