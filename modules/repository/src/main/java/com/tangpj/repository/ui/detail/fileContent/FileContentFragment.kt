package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.github.core.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding

class FileContentFragment : BaseFragment<FragmentFileContentBinding>(){

    lateinit var binding: FragmentFileContentBinding

    override fun onCreateBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): FragmentFileContentBinding{
        binding = FragmentFileContentBinding.inflate(inflater, container, false)
        return binding
    }



}