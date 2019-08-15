package com.tangpj.repository.ui.detail.fileContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.ui.detail.convertToGitObject
import com.tangpj.repository.vo.FileContent
import javax.inject.Inject

class FileContentFragment : BaseFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var mBinding: FragmentFileContentBinding? = null
    private lateinit var fileContentViewModel: FileContentViewModel

    override fun onCreateContentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?): ViewDataBinding?{
        if (mBinding == null){
            val binding = FragmentFileContentBinding.inflate(inflater, container, false)
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
            binding.lifecycleOwner = this
            mBinding = binding
        }

        return mBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

