package com.tangpj.github.di

import androidx.paging.Config

data class PagingConfig(
        val pageSize: Int,
        val initialLoadSizeHint: Int,
        val enablePlaceholders: Boolean = true){

    fun getConfig() = Config(
            pageSize = pageSize,
            initialLoadSizeHint =  initialLoadSizeHint,
            enablePlaceholders = enablePlaceholders)
}