package com.tangpj.github.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tangpj.recurve.resource.NetworkState
import com.tangpj.recurve.resource.Status

@BindingAdapter(value = [
    "state",
    "errorPrompt",
    "emptyPrompt",
    "loadingPrompt",
    "isShowEmptyPrompt"])
fun TextView.loadingPrompt(
        status: Status,
        errorPrompt: String?,
        emptyPrompt: String?,
        loadingPrompt: String,
        isSHowEmptyPrompt: Boolean = false){

    text = when (status) {
        Status.LOADING -> loadingPrompt
        Status.ERROR -> errorPrompt
        Status.SUCCESS -> if (isSHowEmptyPrompt) emptyPrompt else ""
    }
}