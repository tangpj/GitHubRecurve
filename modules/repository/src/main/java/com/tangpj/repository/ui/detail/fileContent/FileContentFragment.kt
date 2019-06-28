package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import javax.inject.Inject

class FileContentFragment : BaseFragment<FragmentFileContentBinding>(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentFileContentBinding
    private lateinit var fileContentViewModel: FileContentViewModel

    override fun onCreateBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): FragmentFileContentBinding{
        binding = FragmentFileContentBinding.inflate(inflater, container, false)
        return binding
    }

    override fun onBindingInit(binding: FragmentFileContentBinding) {
        fileContentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FileContentViewModel::class.java)
        binding.html = fileContentViewModel.html
    }


}