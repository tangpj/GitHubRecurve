package com.tangpj.repository.ui.itemcreator

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.widget.ItemCreator
import com.tangpj.recurve.widget.ModulesAdapter
import com.tangpj.repository.databinding.ItemRepositoryBinding
import com.tangpj.repository.vo.Repository

class RepositoryCreator(adapter: ModulesAdapter, creatorType: Int)
    : ItemCreator<Repository, ItemRepositoryBinding>(adapter, creatorType) {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}