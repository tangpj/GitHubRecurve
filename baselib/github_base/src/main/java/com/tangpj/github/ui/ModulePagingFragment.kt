package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.github.databinding.RecyclerViewBinding
import com.tangpj.github.ui.creator.ItemLoadingCreator
import com.tangpj.paging.PageLoadStatus
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import timber.log.Timber

/**
 *
 * @className: 模块化Paging
 * @author: Tang
 * @createTime: 2019-04-15 15:45
 */
abstract class ModulePagingFragment: RecurveDaggerListFragment(){

    private lateinit var binding: FragmentBaseRecyclerViewBinding
    private lateinit var loadingCreator: ItemLoadingCreator

    open fun onBindingInit(binding: ViewDataBinding){}

    @Suppress("CAST_NEVER_SUCCEEDS")
    final override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?,
                                       savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentBaseRecyclerViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        onBindingInit(binding)
        val recyclerViewBinding = binding.inRv as? RecyclerViewBinding
        recyclerViewBinding?.let {
            initRecyclerView(it.rvContent)
        }
        return binding

    }

    fun loading(pageLoadingInvoke: PageLoading.() -> Unit){
        val loading = PageLoading()
        loading.pageLoadingInvoke()
        binding.listing = loading.listing
        loadingCreator = ItemLoadingCreator(adapter)
        adapter.addCreator(loadingCreator)
        loading.listing?.pagedList?.observe(this, Observer {

        })
        loading.listing?.pageLoadState?.observe(this, Observer { pageLoadState ->
            binding.isShowLoading = adapter.itemCount <= 0
            if (pageLoadState.status != PageLoadStatus.REFRESH){
                loadingCreator.networkState = pageLoadState.networkState
            }else{
                loadingCreator.networkState = null
            }
            Timber.d("""load status = ${pageLoadState.status};
                    netState = ${pageLoadState.networkState.status}; 
                    msg = ${pageLoadState.networkState.msg}""")
        })


    }

}