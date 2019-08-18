package com.tangpj.github.ui.loadState

interface PagedLoadStateInit{
    fun <T> pagedLoading(pageLoadingInvoke: PageLoading<T>.() -> Unit)
}