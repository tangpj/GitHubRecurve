package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.github.ui.creator.ItemLoadingCreator
import com.tangpj.paging.PageLoadStatus
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment

/**
 *
 * @className: 模块化Paging
 * @author: Tang
 * @createTime: 2019-04-15 15:45
 */
abstract class ModulePagingFragment: RecurveDaggerListFragment(){

    private lateinit var binding: FragmentBaseRecyclerViewBinding
    private lateinit var loadingCreator: ItemLoadingCreator

    abstract fun onBindingInit(binding: ViewDataBinding)

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentBaseRecyclerViewBinding.inflate(inflater, container, false)

        binding.setLifecycleOwner(this)
        onBindingInit(binding)
        initRecyclerView(binding.rvContent)
        adapter.addCreator(loadingCreator)
        return binding

    }

    fun loading(pageLoadingInvoke: PageLoading.() -> Unit){
        val loading = PageLoading()
        loading.pageLoadingInvoke()
        binding.pageLoadState = loading.pageLoadState
        binding.retryCallback = loading.refresh
        loadingCreator = ItemLoadingCreator(adapter)
        loading.pageLoadState?.let {
            it.observe(this, Observer { pageLoadState ->
                if (pageLoadState.status != PageLoadStatus.REFRESH){
                    loadingCreator.networkState = pageLoadState.networkState
                }else{
                    loadingCreator.networkState = null
                }
            })

        }
        loading.retry?.let {
            it.observe(this, Observer { retry ->
                loadingCreator.retry = retry

            })
        }


    }

}