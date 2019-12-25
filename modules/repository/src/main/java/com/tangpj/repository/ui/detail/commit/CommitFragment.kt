package com.tangpj.repository.ui.detail.commit

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Option
import arrow.core.extensions.option.foldable.get
import arrow.core.getOrElse
import arrow.core.toOption
import arrow.fx.extensions.io.applicative.just
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.entity.domain.commit.Commit
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
        branchViewModel.currentBranch.observe(this, Observer {
            setCommitQuery(it)
        })
        setCommitQuery(branchViewModel.currentBranch.value)
    }

    private fun setCommitQuery(branch: String?){
        arguments.toOption()
                .map { CommitFragmentArgs.fromBundle(it) }
                .flatMap { it.convertToCommitsQuery(branch ?: "master") }
                .fold({
                    Timber.d("gitObjectQuery is null")
                }, {
                    Timber.d("git object = ${it.gitObjectQuery.repoDetailQuery} author = ${it.author}")
                    commitsViewModel.setCommitQuery(it)
                })
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

    private fun CommitFragmentArgs.convertToCommitsQuery(branch: String) : Option<CommitsQuery>{
            val gitObjectQuery = repoDetailQuery?.let {
                GitObjectQuery(
                        repoDetailQuery = it,
                        branch = branch
                )
            }
        return gitObjectQuery.toOption().map { CommitsQuery(it, author) }
    }
}



