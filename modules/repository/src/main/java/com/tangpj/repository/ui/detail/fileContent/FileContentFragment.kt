package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.ui.BaseFragment
import com.tangpj.recurve.dagger2.RecurveDaggerFragment
import com.tangpj.recurve.resource.Resource.Companion.loading
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.valueObject.query.FileContentQuery
import javax.inject.Inject

private const val KEY_FILE_CONTENT_QUERY =
        "com.tangpj.repository.ui.detail.fileContent.KEY_FILE_CONTENT_QUERY"

class FileContentFragment : BaseFragment(){

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
            savedInstanceState: Bundle?): ViewDataBinding? {
        binding = FragmentFileContentBinding.inflate(inflater, container, false)
        onBindingInit(binding)
        return binding
    }

    private fun onBindingInit(binding: FragmentFileContentBinding) {
        binding.setLifecycleOwner(this)
        fileContentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FileContentViewModel::class.java)
        binding.html = fileContentViewModel.html
        val fileContentQuery = arguments?.getParcelable<FileContentQuery>(KEY_FILE_CONTENT_QUERY)
        fileContentQuery?.let {
            fileContentViewModel.loadFileContentByQuery(it)
        }

        loading {
            pageLoadState = repoViewModel.pageLoadState
            refresh = repoViewModel.refresh
            retry = repoViewModel.repoRetry
        }
    }


}