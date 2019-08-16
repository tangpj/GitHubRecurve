package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tangpj.github.databinding.FragmentBaseBinding
import com.tangpj.github.ui.creator.ItemLoadingCreator
import com.tangpj.paging.PageLoadStatus
import com.tangpj.recurve.dagger2.RecurveDaggerFragment
import com.tangpj.recurve.resource.Status
import com.tangpj.recurve.ui.fragment.RecurveFragment
import com.tangpj.recurve.ui.strategy.LoadingStrategy

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
        _binding.lifecycleOwner = this
        contentBinding?.apply {
            _binding.flRoot.addView(this.root)
            lifecycleOwner = this@BaseFragment
            root.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
            root.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT

        }
        return _binding
    }

    fun <Data > loading(loadingInvoke : Loading<Data>.() -> Unit){
        val loading = Loading<Data>()
        loading.loadingInvoke()
        loading.resource?.observe(this, Observer {
            contentBinding?.root?.visibility =
                    if (it.data != null){
                        View.VISIBLE
                    }else{
                        View.GONE
                    }
            _binding.resource = it
        })

    }
}