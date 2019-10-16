package com.tangpj.github.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recurve.adapter.ModulesAdapter
import com.recurve.adapter.creator.ItemCreator
import com.recurve.core.resource.NetworkState
import com.tangpj.github.databinding.ItemLoadStateBinding

/**
 *
 *
 * @className: ItemLoadingCreator
 * @author: tangpengjian113
 * @createTime: 2019-05-23 21:11
 */
class ItemLoadingCreator
    : ItemCreator<NetworkState, ItemLoadStateBinding>( 99999){

    var networkState: NetworkState? = null
    set(value) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        field = value
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                mAdapter.notifyModulesItemRemoved(this, super.getItemCount())
            } else {
                mAdapter.notifyModulesItemInserted(this, super.getItemCount())
            }
        } else if (hasExtraRow && previousState != value) {
            mAdapter.notifyModulesItemChanged(this,getItemCount() - 1)
        }
        field = value
    }

    var retry: (() -> Unit)? = null

    override fun onBindItemView(binding: ItemLoadStateBinding, e: NetworkState, inCreatorPosition: Int) {
        binding.networkState = networkState
        binding.retryCallback = retry
    }

    override fun onBindCreator(adapter: ModulesAdapter) {
        super.onBindCreator(adapter)
        addItem(NetworkState.LOADING)
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ItemLoadStateBinding {
        val inflater = LayoutInflater.from(parent.context)
        return  ItemLoadStateBinding.inflate(inflater, parent, false)
    }

    override fun getItemCount(): Int = if (hasExtraRow()) 1 else 0

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS


}