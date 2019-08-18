package com.tangpj.github.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.github.databinding.PageLoadingStateBinding
import com.tangpj.github.databinding.RecyclerViewBinding
import com.tangpj.github.ui.creator.ItemLoadingCreator
import com.tangpj.paging.Listing
import com.tangpj.paging.PageLoadStatus
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import com.tangpj.recurve.resource.NetworkState
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

    fun <T> loading(pageLoadingInvoke: PageLoading<T>.() -> Unit){
        val loading = PageLoading<T>()
        loading.pageLoadingInvoke()
        val layoutInflater = LayoutInflater.from(binding.flContent.context)
        val pageLoadingStateBinding =
                PageLoadingStateBinding.inflate(layoutInflater, binding.flContent, false)
        pageLoadingStateBinding.lifecycleOwner = this
        val params = pageLoadingStateBinding.root.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        params.width = FrameLayout.LayoutParams.MATCH_PARENT
        params.height= FrameLayout.LayoutParams.MATCH_PARENT

        binding.flContent.addView(pageLoadingStateBinding.root, params)
        loading.listing?.observe(this, Observer {
            loadingCreator = ItemLoadingCreator(adapter)
            adapter.addCreator(loadingCreator)
            observerListing(it, pageLoadingStateBinding)

        })

    }

    private fun observerListing(listing: Listing<*>, pageLoadingStateBinding: PageLoadingStateBinding){
        listing.pageLoadState.observe(this, Observer { pageLoadState ->
            pageLoadingStateBinding.listing = listing
            pageLoadingStateBinding.isShowLoading = adapter.itemCount <= 0
            if (pageLoadState.status != PageLoadStatus.REFRESH){
                loadingCreator.networkState = pageLoadState.networkState
            }else{
                loadingCreator.networkState = null
            }
            if (pageLoadState.networkState == NetworkState.SUCCESS){
                Timber.d("adapter count = ${adapter.itemCount}")
            }
            Timber.d("""load status = ${pageLoadState.status};
                    netState = ${pageLoadState.networkState.status}; 
                    msg = ${pageLoadState.networkState.msg}""")
        })
    }

}