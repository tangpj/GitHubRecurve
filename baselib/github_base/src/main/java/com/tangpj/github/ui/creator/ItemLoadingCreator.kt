package com.tangpj.github.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
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
    : ItemCreator<Unit, ItemLoadStateBinding>(adapter, 99999){

    var networkState: LiveData<NetworkState>? = null

    var retry: LiveData<() -> Unit>? = null

    override fun onBindItemView(itemHolder: RecurveViewHolder<ItemLoadStateBinding>, e: Unit?, inCreatorPosition: Int) {
        itemHolder.binding.networkState = networkState
        itemHolder.binding.retryCallback = retry
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
        return RecurveViewHolder(binding)
    }

    override fun getItemCount(): Int {
        networkState?.value?.let {
            return@getItemCount if (it.status == Status.LOADING || it.status == Status.ERROR){
                1
            }else {
                0
            }
        }
        return 0

    }


}