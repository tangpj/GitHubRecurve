package com.tangpj.github.ui

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.Resource

class Loading{
    var resource: LiveData<Resource<Any>>? = null

    var retry: (() -> Unit)? = null
}
