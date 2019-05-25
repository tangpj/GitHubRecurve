package com.tangpj.github.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.github.databinding.ItemLoadStateBinding
import com.tangpj.recurve.resource.NetworkState
import com.tangpj.recurve.resource.Status

/**
 *
 *
 * @className: ItemLoadingCreator
 * @author: tangpengjian113
 * @createTime: 2019-05-23 21:11
 */
class ItemLoadingCreator(adapter: ModulesAdapter)
    : ItemCreator<NetworkState, ItemLoadStateBinding>(adapter, 99999){

    var networkState: NetworkState? = null
    set(value) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        field = value
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                adapter.notifyModulesItemRemoved(this, super.getItemCount())
            } else {
                adapter.notifyModulesItemInserted(this, super.getItemCount())
            }
        } else if (hasExtraRow && previousState != value) {
            adapter.notifyModulesItemChanged(this,getItemCount() - 1)
        }
        field = value
    }

    var retry: (() -> Unit)? = null

    override fun onBindItemView(itemHolder: RecurveViewHolder<ItemLoadStateBinding>, e: NetworkState?, inCreatorPosition: Int) {
        itemHolder.binding.networkState = networkState
        itemHolder.binding.retryCallback = retry
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
        return RecurveViewHolder(binding)
    }

    override fun getItemCount(): Int = if (hasExtraRow()) 1 else 0

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS


}