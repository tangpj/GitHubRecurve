package com.tangpj.repository.ui.detail.commit

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Option
import arrow.core.extensions.option.foldable.get
import arrow.core.getOrElse
import arrow.fx.extensions.io.applicative.just
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.CommitCreator
import com.tangpj.repository.ui.detail.BranchViewModel
import com.tangpj.repository.valueObject.query.CommitsQuery
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.vo.CommitVo
import timber.log.Timber
import javax.inject.Inject

class CommitFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var commitsViewModel: CommitsViewModel
    private lateinit var branchViewModel: BranchViewModel

    @Inject
    lateinit var commitCreator: CommitCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val argOption: Option<Bundle> = Option.fromNullable(arguments)
        val commitArgs = argOption.map {
            CommitFragmentArgs.fromBundle(it)
        }

        commitArgs.exists { arg ->
            Timber.d("git object = ${arg.repoDetailQuery} author = ${arg.author}")
            branchViewModel.currentBranch.observe(this, Observer{
                val gitObject = arg.convertToGitObjectQuery(it)
                gitObject ?: return@Observer
                val commitsQuery = CommitsQuery(
                        gitObject,
                        arg.author
                )
                commitsViewModel.setCommitQuery(commitsQuery)

            })
            true
        }

    }

    override fun onBindingInit(binding: ViewDataBinding) {
        super.onBindingInit(binding)
        commitsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CommitsViewModel::class.java)
        branchViewModel = ViewModelProviders.of(this , viewModelFactory)
                .get(BranchViewModel::class.java)

        pagedLoading<CommitVo> {
            listing = commitsViewModel.commitsListing
        }

        addItemCreator(commitCreator)

        commitsViewModel.pageList.observe(this, Observer {
            commitCreator.submitList(it)
        })

    }

}


private fun CommitFragmentArgs.convertToGitObjectQuery(branch: String) =
        repoDetailQuery?.let {
            GitObjectQuery(
                    repoDetailQuery = it,
                    branch = branch
            )
        }
