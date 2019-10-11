package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.tangpj.github.R

class WrapperDialogFragment : DialogFragment(){

    var navHostFragment = MutableLiveData<NavHostFragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            val arg = WrapperDialogFragmentArgs.fromBundle(it)
            val layoutId =  R.layout.fragment_nav_container
            val binding =
                    DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, container, false)
            val fragment = NavHostFragment.create(arg.graphId, arg.args)
            childFragmentManager.beginTransaction().add(R.id.nav_host_container, fragment)
                    .setPrimaryNavigationFragment(fragment).commitNow()
            navHostFragment.value = fragment
            return binding.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}