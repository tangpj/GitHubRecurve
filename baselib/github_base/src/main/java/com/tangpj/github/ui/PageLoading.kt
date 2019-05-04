package com.tangpj.github.ui

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.tangpj.paging.PageLoadState

class PageLoading{
    var pagedList: LiveData<PagedList<Any>>? = null

    var pageLoadState: LiveData<PageLoadState>? = null

    var retry: (() -> Unit)? = null
}