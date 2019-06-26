package com.tangpj.github.core

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.ui.fragment.RecurveFragment
import com.tangpj.recurve.ui.strategy.LoadingStrategy

abstract class BaseFragment<Binding: ViewDataBinding> : RecurveFragment(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}