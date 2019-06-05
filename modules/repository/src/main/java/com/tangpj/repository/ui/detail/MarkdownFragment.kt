package com.tangpj.repository.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tangpj.github.core.BaseFragment
import com.tangpj.repository.databinding.FragmentMarkdownBinding

class MarkdownFragment : BaseFragment(){

    lateinit var binding: FragmentMarkdownBinding

    override fun onCreateBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): ViewDataBinding{
        binding = FragmentMarkdownBinding.inflate(inflater, container, false)
        return binding
    }



}