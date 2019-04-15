package com.tangpj.github.ui

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.Resource

interface Loading{

    fun onResource(resource: LiveData<Resource<Any>>)

    fun onRetry(retry: () -> Unit)
}
