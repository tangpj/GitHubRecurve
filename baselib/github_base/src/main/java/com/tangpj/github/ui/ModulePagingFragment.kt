package com.tangpj.github.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.recurve.core.resource.NetworkState
import com.recurve.core.resource.Status
import com.recurve.dagger2.RecurveDaggerListFragment
import com.recurve.paging.Listing
import com.recurve.paging.PageLoadStatus
import com.tangpj.github.R
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.github.databinding.LoadingStateBinding
import com.tangpj.github.databinding.PageLoadingStateBinding
import com.tangpj.github.databinding.RecyclerViewBinding
import com.tangpj.github.ui.creator.ItemLoadingCreator
import com.tangpj.github.ui.loadState.Loading
import com.tangpj.github.ui.loadState.PageLoading
import com.tangpj.github.ui.loadState.RealLoadState
import timber.log.Timber

/**
 *
 * @className: 模块化Paging
 * @author: Tang
 * @createTime: 2019-04-15 15:45
 */
abstract class ModulePagingFragment: RecurveDaggerListFragment(){

    private lateinit var binding: FragmentBaseRecyclerViewBinding
    private  var loadingCreator: ItemLoadingCreator? = null

    open fun onBindingInit(binding: ViewDataBinding){}

    @Suppress("CAST_NEVER_SUCCEEDS")
    final override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?,
                                       savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentBaseRecyclerViewBinding.inflate(inflater, container, false)
        onBindingInit(binding)
        val recyclerViewBinding = binding.inRv as? RecyclerViewBinding
        recyclerViewBinding?.let {
            initRecyclerView(it.rvContent)
        }
        return binding

    }

    fun <T> pagedLoading(pageLoadingInvoke: PageLoading<T>.() -> Unit){
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
            if (loadingCreator == null){
                val itemLoadingCreator= ItemLoadingCreator()
                loadingCreator = itemLoadingCreator
                adapter.addCreator(itemLoadingCreator)
            }

            observerListing(it, pageLoadingStateBinding)

        })

    }

    fun <T> loading(loadingInvoke: Loading<T>.() -> Unit) {
        val realLoadState =
                RealLoadState<T>(binding.flContent)
        realLoadState.createLoadBinding { container, loading ->
            val inflater = LayoutInflater.from(container.context)
            val loadStateBinding = LoadingStateBinding.inflate(inflater, container, false)
            loadStateBinding.lifecycleOwner = this
            loadStateBinding.retry = loading.retry
            loading.resource?.observe(this, Observer {
                Timber.d("""netState = ${it.networkState.status}; 
                    msg = ${it.networkState.msg}""")
                loadStateBinding.resource = it
            })
            loadStateBinding
        }
        realLoadState.loading(loadingInvoke)
    }

    private fun observerListing(listing: Listing<*>, pageLoadingStateBinding: PageLoadingStateBinding){
        listing.pageLoadState.observe(this, Observer { pageLoadState ->
            pageLoadingStateBinding.listing = listing
            pageLoadingStateBinding.isShowLoading = adapter.itemCount == 0
            if (pageLoadState.status != PageLoadStatus.REFRESH){
                loadingCreator?.networkState = pageLoadState.networkState
            }else{
                loadingCreator?.networkState = null
            }
            pageLoadingStateBinding.errorMsg = {
                when(pageLoadState.networkState.status){
                    Status.SUCCESS -> {
                        if (adapter.itemCount == 0) getString(R.string.result_is_empty) else ""
                    }
                    Status.LOADING -> ""
                    Status.ERROR -> pageLoadState.networkState.msg ?: getString(R.string.unknown_error)
                }
            }

            pageLoadingStateBinding.showError = {
                when(pageLoadState.networkState.status){
                    Status.LOADING -> false
                    Status.SUCCESS -> adapter.itemCount == 0
                    Status.ERROR -> true
                }
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