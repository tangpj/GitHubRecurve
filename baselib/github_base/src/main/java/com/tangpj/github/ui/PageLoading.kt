package com.tangpj.github.ui

import androidx.lifecycle.LiveData
import com.tangpj.paging.PageLoadState

class PageLoading{
    var pageLoadState: LiveData<PageLoadState>? = null

    var refresh: LiveData<(() -> Unit)>? = null

    var retry: LiveData<(() -> Unit)>? = null
}