package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.vo.FileContent
import javax.inject.Inject

class FileContentFragment : BaseFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentFileContentBinding
    private lateinit var fileContentViewModel: FileContentViewModel

    override fun onCreateContentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?): ViewDataBinding{
        binding = FragmentFileContentBinding.inflate(inflater, container, false)
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        fileContentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FileContentViewModel::class.java)
        binding.fileContent = fileContentViewModel.fileContent
        val gitObjectQuery = arguments?.let{
            val fileContentQuery = FileContentFragmentArgs.fromBundle(it)
            fileContentQuery.convertToGitObject()
        }

        gitObjectQuery?.let {
            fileContentViewModel.loadFileContentByQuery(it)
        }

        loading<FileContent> {
            resource = fileContentViewModel.fileContent
        }
    }

}

internal fun FileContentFragmentArgs.convertToGitObject() : GitObjectQuery? {
    return repoDetailQuery?.let {
        GitObjectQuery(
                repoDetailQuery = it,
                branch = branch,
                path = path)
    }
}