package com.tangpj.repository.ui

import com.tangpj.recurve.widget.RecurveViewHolder
import com.tangpj.repository.databinding.ItemRepositoryBinding
import com.tangpj.repository.vo.Repository

class RepositoryHolder(binding: ItemRepositoryBinding)
    : RecurveViewHolder<Repository, ItemRepositoryBinding>(binding){
    override fun setData(data: Repository, binding: ItemRepositoryBinding, position: Int) {
        binding.repositor = data
    }
}