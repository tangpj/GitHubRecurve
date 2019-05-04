package com.tangpj.github.ui

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.NetworkState
import com.tangpj.recurve.resource.Resource

class PageLoading<T>{
    var resource: LiveData<Resource<T>>? = null

    var networkState: LiveData<NetworkState>? = null

    var retry: (() -> Unit)? = null
}