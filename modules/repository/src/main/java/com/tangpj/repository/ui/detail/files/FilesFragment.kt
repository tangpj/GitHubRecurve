package com.tangpj.repository.ui.detail.files

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.recurve.resource.Status
import com.tangpj.repository.ui.creator.FileItemCreator
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.entry.vo.FileItem
import com.tangpj.repository.entry.vo.FileType
import com.tangpj.repository.ui.detail.fileContent.FileContentFragmentDirections
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
        gitObjectQuery?: return
        filesViewModel.loadFileTreeByQuery(fileTreeQuery = gitObjectQuery )
        addItemCreator(fileItemCreator)

        filesViewModel.fileItems.observe(this, Observer { resource ->
            if (resource.networkState.status != Status.LOADING){
                fileItemCreator.setDataList(resource.data ?: emptyList())
            }
        })

        fileItemCreator.setOnItemClickListener { view, e, creatorPosition ->
            e ?: return@setOnItemClickListener
            val action = if (e.type == FileType.TREE){
                FilesFragmentDirections.actionFiles().apply {
                    this.repoDetailQuery = gitObjectQuery.repoDetailQuery
                    this.branch = gitObjectQuery.branch
                    this.path = gitObjectQuery.nextPath(e.name)
                }
            }else {
                FilesFragmentDirections.actionFilesToFlContent().apply {
                    this.repoDetailQuery = gitObjectQuery.repoDetailQuery
                    this.branch = gitObjectQuery.branch
                    this.path = gitObjectQuery.nextPath(e.name)
                }

            }
            findNavController().navigate(action)
        }

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
