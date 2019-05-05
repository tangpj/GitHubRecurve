package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import com.tangpj.recurve.resource.Resource
import com.tangpj.recurve.resource.Status

/**
 *
 * @className: 模块化Paging
 * @author: tangpengjian113
 * @createTime: 2019-04-15 15:45
 */
abstract class ModulePagingFragment: RecurveDaggerListFragment(){

    private lateinit var binding: FragmentBaseRecyclerViewBinding

    abstract fun onBindingInit(binding: ViewDataBinding)

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentBaseRecyclerViewBinding.inflate(inflater, container, false)

        binding.setLifecycleOwner(this)
        onBindingInit(binding)
        initRecyclerView(binding.rvContent)
        return binding

    }

    fun <T: Any> loading(pageLoadingInvoke: PageLoading<PagedList<T>>.() -> Unit){
        val loading = PageLoading<PagedList<T>>()
        loading.pageLoadingInvoke()
        binding.networkState = loading.networkState
        binding.retryCallback = loading.retry
        loading.resource?.let {
            val resource: LiveData<Resource<Any>> = Transformations.map(it){ _resource ->
                return@map when(_resource.networkState.status){
                    Status.LOADING -> Resource.loading<Any>(getData(_resource.data))
                    Status.ERROR -> Resource.error<Any>(
                            _resource.networkState.msg ?: "", getData(_resource.data))
                    Status.SUCCESS -> Resource.success<Any>(getData(_resource.data))
                }
        }
            binding.resource = resource
        }


    }

    private fun <T> getData(pagedList: PagedList<T>?): PagedList<T>?{
        return when {
            pagedList == null -> null
            pagedList.size == 0 -> null
            else -> pagedList
        }
    }



}