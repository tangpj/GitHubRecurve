package com.tangpj.github.ui

import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.creator.ItemCreator

class Init{

    var loading: Loading? = null

    var creators: List<ItemCreator<Any, *>>? = null

    var diffCallback: DiffUtil.ItemCallback<*>? = null
}