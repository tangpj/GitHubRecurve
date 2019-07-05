package com.tangpj.github.ui

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.Resource
import java.util.*

class Loading<Data>{
    var resource: LiveData<Resource<Data>>? = null

    var retry: (() -> Unit)? = null
}
