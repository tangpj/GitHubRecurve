package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.github.core.BaseFragment
import com.tangpj.repository.databinding.FragmentMarkdownBinding

class FileContentFragment : BaseFragment<FragmentMarkdownBinding>(){

    lateinit var binding: FragmentMarkdownBinding

    override fun onCreateBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): FragmentMarkdownBinding{
        binding = FragmentMarkdownBinding.inflate(inflater, container, false)
        return binding
    }

    override fun initBinding(binding: FragmentMarkdownBinding) {
        super.initBinding(binding)
        binding.webView
    }

}