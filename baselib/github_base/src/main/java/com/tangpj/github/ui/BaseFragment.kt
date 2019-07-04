package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tangpj.github.databinding.FragmentBaseBinding
import com.tangpj.github.ui.creator.ItemLoadingCreator
import com.tangpj.paging.PageLoadStatus
import com.tangpj.recurve.dagger2.RecurveDaggerFragment
import com.tangpj.recurve.ui.fragment.RecurveFragment
import com.tangpj.recurve.ui.strategy.LoadingStrategy

abstract class BaseFragment : RecurveDaggerFragment(){

    lateinit var binding: FragmentBaseBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding
    }

    fun loading(pageLoadingInvoke: PageLoading.() -> Unit){
        val loading = PageLoading()
        loading.pageLoadingInvoke()
        binding.pageLoadState = loading.pageLoadState
        binding.retryCallback = loading.refresh
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