package com.tangpj.github.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.recurve.dagger2.RecurveDaggerFragment
import com.tangpj.github.databinding.FragmentBaseBinding
import com.tangpj.github.databinding.LoadingStateBinding
import com.tangpj.github.ui.loadState.Loading
import com.tangpj.github.ui.loadState.RealLoadState

abstract class BaseFragment : RecurveDaggerFragment(){

    private lateinit var _binding: FragmentBaseBinding
    private var contentBinding: ViewDataBinding? = null

    abstract fun onCreateContentBinding(inflater: LayoutInflater, container: ViewGroup?) : ViewDataBinding?

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    final override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        contentBinding = onCreateContentBinding(inflater, _binding.root as? ViewGroup)
        contentBinding?.apply {
            _binding.flRoot.addView(this.root)
            lifecycleOwner = this@BaseFragment
            root.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
            root.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT

        }
        return _binding
    }

    fun <T> loading(loadingInvoke: Loading<T>.() -> Unit){
        val realLoadState =
                RealLoadState<T>(_binding.flRoot)
        realLoadState.createLoadBinding { container, loading ->
            val inflater = LayoutInflater.from(container.context)
            val loadStateBinding = LoadingStateBinding.inflate(inflater, container, false)
            loadStateBinding.lifecycleOwner = this
            loadStateBinding.retry = loading.retry
            loading.resource?.observe(this, Observer {
                loadStateBinding.resource = it
            })
            loadStateBinding
        }
        realLoadState.loading(loadingInvoke)
    }
}