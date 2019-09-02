package com.tangpj.repository.ui.detail.files


import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.FileItemCreator
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.entry.vo.FileItem
import com.tangpj.repository.entry.vo.FileType
import javax.inject.Inject

class FilesFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var filesViewModel: FilesViewModel

    private lateinit var fileItemCreator: FileItemCreator

    override fun onBindingInit(binding: ViewDataBinding) {
        super.onBindingInit(binding)
        fileItemCreator = FileItemCreator()
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
            resource.data?.let {
                fileItemCreator.setDataList(it)
            }
        })

        fileItemCreator.setOnItemClickListener { _, e, _ ->
            val action = if (e.type == FileType.TREE){
                FilesFragmentDirections.actionFiles().apply {
                    this.repoDetailQuery = gitObjectQuery.repoDetailQuery
                    this.branch = gitObjectQuery.branch
                    path = gitObjectQuery.nextPath(e.name)
                }
            }else {
                FilesFragmentDirections.actionFilesToFlContent().apply {
                    this.repoDetailQuery = gitObjectQuery.repoDetailQuery
                    this.branch = gitObjectQuery.branch
                    path = gitObjectQuery.nextPath(e.name)
                }

            }
            findNavController().navigate(action)
        }

        loading<List<FileItem>>{
            resource = filesViewModel.fileItems
            retry = { filesViewModel.retry() }
        }
    }


    override fun initRecyclerView(rv: RecyclerView) {
        super.initRecyclerView(rv)
        (rv.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
    }

}

private fun FilesFragmentArgs.convertToGitObjectQuery() =
       repoDetailQuery?.let {
           GitObjectQuery(
                   repoDetailQuery = it,
                   branch = branch,
                   path = path ?: ""
           )
       }
