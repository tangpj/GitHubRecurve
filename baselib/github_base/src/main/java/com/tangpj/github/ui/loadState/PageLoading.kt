package com.tangpj.github.ui.loadState

import androidx.lifecycle.LiveData
import com.tangpj.paging.Listing

class PageLoading<T>{
    var listing: LiveData<Listing<T>>? = null

}