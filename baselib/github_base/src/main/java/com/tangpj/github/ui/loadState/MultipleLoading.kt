package com.tangpj.github.ui.loadState

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tangpj.recurve.resource.Resource


/**
 * progressbar loading
 * Can be configured multiple
 * displayed below the actionbar
 *
 * @className: MultipleLoading
 * @author create by Tang
 * @date  12:39 PM
 */
class MultipleLoading {

    val loadingResource = MediatorLiveData<Resource<*>>()

    /**
     * Monitor multiple resource network state changes
     *
     * @method: loadingResources
     * @author create by Tang
     * @date 2019/9/15 1:27 PM
     */
    @Suppress("UNCHECKED_CAST")
    fun <T: Resource<*>> loadingResources(vararg resource: LiveData<T>) {
        resource.toList()
                .forEach {
                    loadingResource.addSource(it){ resource ->
                        loadingResource.value = resource
                    }
                }
    }

}