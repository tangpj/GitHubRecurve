package com.tangpj.github.ui.loadState

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding

class RealLoadState<T>(
        private val container: ViewGroup){

    private lateinit var mAction:
            (container: ViewGroup, Loading<T>) -> ViewDataBinding

    fun createLoadBinding(
            action: ((container: ViewGroup, Loading<T>) -> ViewDataBinding)) {
        mAction = action
    }

    fun loading(loadingInvoke: Loading<T>.() -> Unit) {
        val loading = Loading<T>()
        loading.loadingInvoke()
        val loadStateBinding = mAction.invoke(container, loading)
        val params =
                loadStateBinding.root.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        params.width = FrameLayout.LayoutParams.MATCH_PARENT
        params.height = FrameLayout.LayoutParams.MATCH_PARENT

        container.addView(loadStateBinding.root, params)

    }
}