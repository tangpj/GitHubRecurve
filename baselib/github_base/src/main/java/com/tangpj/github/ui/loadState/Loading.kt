package com.tangpj.github.ui.loadState

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.Resource

class Loading<T>{

    var resource: LiveData<Resource<T>>? = null

    var retry: (() -> Unit)? = null
}
