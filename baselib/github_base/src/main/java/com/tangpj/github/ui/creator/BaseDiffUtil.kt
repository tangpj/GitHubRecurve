package com.tangpj.github.ui.creator

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtil <E> : DiffUtil.ItemCallback<E>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: E, newItem: E): Boolean =
            oldItem == newItem

    override fun getChangePayload(oldItem: E, newItem: E): Any? {
        return newItem
    }
}