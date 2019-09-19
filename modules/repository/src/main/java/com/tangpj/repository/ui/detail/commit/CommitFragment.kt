package com.tangpj.repository.ui.detail.commit

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.CommitCreator
import com.tangpj.repository.ui.detail.files.FilesFragmentArgs
import com.tangpj.repository.valueObject.query.CommitsQuery
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.vo.CommitVo
import timber.log.Timber
import javax.inject.Inject

class CommitFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var commitsViewModel: CommitsViewModel

    @Inject
    lateinit var commitCreator: CommitCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            val arg = CommitFragmentArgs.fromBundle(it)
            Timber.d("git object = ${arg.repoDetailQuery} author = ${arg.author}")
            val gitObject = arg.convertToGitObjectQuery()
            gitObject ?: return
            val commitsQuery = CommitsQuery(
                    gitObject,
                    arg.author
            )
            commitsViewModel.setCommitQuery(commitsQuery)
        }
    }

    override fun onBindingInit(binding: ViewDataBinding) {
        super.onBindingInit(binding)
        commitsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CommitsViewModel::class.java)

        pagedLoading<CommitVo> {
            listing = commitsViewModel.commitsListing
        }

        addItemCreator(commitCreator)

        commitsViewModel.pageList.observe(this, Observer {
            commitCreator.submitList(it)
        })

    }

}


private fun CommitFragmentArgs.convertToGitObjectQuery() =
        repoDetailQuery?.let {
            GitObjectQuery(
                    repoDetailQuery = it,
                    branch = branch
            )
        }
