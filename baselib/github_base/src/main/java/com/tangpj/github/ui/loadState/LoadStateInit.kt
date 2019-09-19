package com.tangpj.github.ui.loadState

interface LoadStateInit{

    fun <T> loading(loadingInvoke: Loading<T>.() -> Unit)
}