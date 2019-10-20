package com.tangpj.repository.ui.detail.refs

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.entity.domain.Ref
import com.tangpj.repository.ui.creator.RefCreator
import com.tangpj.repository.ui.detail.BranchViewModel
import com.tangpj.repository.valueObject.query.RefsQuery
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Fetch a list of refs from the repository
 *
 * @className: RefsFragment
 * @author create by Tang
 * @date  12:44 PM
 */

class RefsFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var refsViewModel: RefsViewModel
    private lateinit var branchViewModel: BranchViewModel

    @Inject
    lateinit var refCreator: RefCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            val arg = RefsFragmentArgs.fromBundle(it)
            Timber.d("""refs args: repoDetailQuery =  ${arg.repoDetailQuery}
                | prefix = ${arg.prefix}
            """.trimMargin())
            arg.convertToRefsQuery()?.apply {
                refsViewModel.setRefsQuery(this)
            }
        }
    }

    override fun onBindingInit(binding: ViewDataBinding) {
        super.onBindingInit(binding)
        refsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RefsViewModel::class.java)
        branchViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BranchViewModel::class.java)

        pagedLoading<Ref> {
            listing = refsViewModel.refListing
        }

        addItemCreator(refCreator)
        refsViewModel.pageList.observe(this, Observer {
            refCreator.submitList(it)
        })
        refCreator.selectBranch = branchViewModel.currentBranch.value

    }
}

private fun RefsFragmentArgs.convertToRefsQuery() =
        repoDetailQuery?.let {
            RefsQuery(
                    repoDetailQuery = it,
                    prefix = prefix
            )
        }