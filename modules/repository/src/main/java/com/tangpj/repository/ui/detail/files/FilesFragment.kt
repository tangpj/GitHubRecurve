package com.tangpj.repository.ui.detail.files


import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import arrow.core.*
import arrow.core.extensions.option.foldable.get
import arrow.core.extensions.option.traverseFilter.filter
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.FileItemCreator
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.entity.domain.file.FileItem
import com.tangpj.repository.entity.domain.file.FileType
import com.tangpj.repository.ui.detail.BranchViewModel
import timber.log.Timber
import javax.inject.Inject

class FilesFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var filesViewModel: FilesViewModel
    private lateinit var branchViewModel: BranchViewModel

    private lateinit var fileItemCreator: FileItemCreator

    override fun onBindingInit(binding: ViewDataBinding) {
        super.onBindingInit(binding)
        fileItemCreator = FileItemCreator()
        filesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FilesViewModel::class.java)

        branchViewModel.currentBranch.observe(this, Observer { branch ->

            val query = arguments.toOption()
                    .map { FilesFragmentArgs.fromBundle(it) }
                    .map { it.convertToGitObjectQuery(branch)}
                    .fold({ Timber.d("queryEmpty")}, {

                    })
        })
        val gitObjectQuery = arguments?.let{
            val filesArgs= FilesFragmentArgs.fromBundle(it)
            filesArgs.convertToGitObjectQuery()
        }
        gitObjectQuery?: return


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
                    path = gitObjectQuery.nextPath(e.name)
                }
            }else {
                FilesFragmentDirections.actionFilesToFlContent().apply {
                    this.repoDetailQuery = gitObjectQuery.repoDetailQuery
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

private fun FilesFragmentArgs.convertToGitObjectQuery(branch: String) =
           GitObjectQuery(
                   repoDetailQuery = repoDetailQuery,
                   branch = branch,
                   path = path)
