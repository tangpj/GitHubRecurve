package com.tangpj.repository.ui.detail.files

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.FileItemCreator
import com.tangpj.repository.ui.repositories.ReposViewModel
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.vo.FileItem
import javax.inject.Inject

class FilesFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var filesViewModel: FilesViewModel

    var fileItemCreator = FileItemCreator(adapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onBindingInit(binding: ViewDataBinding) {
        super.onBindingInit(binding)
        filesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FilesViewModel::class.java)
        val gitObjectQuery = arguments?.let{
            val filesArgs= FilesFragmentArgs.fromBundle(it)
            filesArgs.convertToGitObjectQuery()
        }
        gitObjectQuery?.let {
            filesViewModel.loadFileTreeByQuery(fileTreeQuery = it )
        }

        addItemCreator(fileItemCreator)

        filesViewModel.fileItems.observe(this, Observer { resource ->
            resource?.data?.let {
                fileItemCreator.addItems(it)
            }
        })
        loading<List<FileItem>>{
            resource = filesViewModel.fileItems
        }
    }

}

private fun FilesFragmentArgs.convertToGitObjectQuery() =
       repoDetailQuery?.let {
           GitObjectQuery(
                   repoDetailQuery = it,
                   branch = branch,
                   path = path
           )
       }
