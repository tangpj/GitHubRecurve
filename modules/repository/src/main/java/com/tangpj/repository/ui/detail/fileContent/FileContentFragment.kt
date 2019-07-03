package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.valueObject.query.FileContentQuery
import javax.inject.Inject

private const val KEY_FILE_CONTENT_QUERY =
        "com.tangpj.repository.ui.detail.fileContent.KEY_FILE_CONTENT_QUERY"

class FileContentFragment : BaseFragment<FragmentFileContentBinding>(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentFileContentBinding
    private lateinit var fileContentViewModel: FileContentViewModel

    companion object{
        fun create(query: FileContentQuery) =
                FileContentFragment().apply {
                    arguments = Bundle(1).apply {
                        putParcelable(KEY_FILE_CONTENT_QUERY, query)
                    }
                }
    }

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