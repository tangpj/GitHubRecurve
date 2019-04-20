package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.adapter.creator.Creator
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.paging.addPagedCreator
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment

/**
 *
 * @className: 模块化Paging
 * @author: tangpengjian113
 * @createTime: 2019-04-15 15:45
 */
class ModulePagingFragment: RecurveDaggerListFragment(){

    private lateinit var binding: FragmentBaseRecyclerViewBinding

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentBaseRecyclerViewBinding.inflate(inflater, container, false)

        binding.setLifecycleOwner(this)
        initRecyclerView(binding.recyclerContent.rv)
        return binding

    }

    fun loading(loadingInvoke: Loading.() -> Unit){
        val loading = Loading()
        loading.loadingInvoke()
        binding.resource = loading.resource
        binding.retryCallback = loading.retry
    }

    fun <E> addItemCreator(creator: ItemCreator<E, *>, diffCallback: DiffUtil.ItemCallback<E>) {
        adapter.addPagedCreator(creator, diffCallback)
    }

    fun <E> addItemCreator(creator: ItemCreator<E, *>,  config: AsyncDifferConfig<E>) {
        adapter.addPagedCreator(creator, config)
    }

}