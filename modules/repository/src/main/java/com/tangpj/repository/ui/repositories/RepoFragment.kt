package com.tangpj.repository.ui.repositories


import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.RepositoryCreator
import com.tangpj.repository.vo.Repo
import timber.log.Timber
import javax.inject.Inject

class RepoFragment: ModulePagingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoViewModel: RepositoryViewModel

    private lateinit var repositoryCreator : RepositoryCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arg = RepoFragmentArgs.fromBundle(arguments)
        Timber.d("user name = ${arg.login}")
        repoViewModel.setRepoOwner(arg.login)
    }

    override fun onBindingInit(binding: ViewDataBinding) {
        repoViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepositoryViewModel::class.java)
        repositoryCreator = RepositoryCreator(adapter, POST_COMPARATOR)
        repoViewModel.pageLoadState.observeForever {
            Timber.d("load status = ${it.status}; netState = ${it.networkState.status}; msg = ${it.networkState.msg}")
        }

        loading {
            pageLoadState = repoViewModel.pageLoadState
            refresh = repoViewModel.refresh
            retry = repoViewModel.repoRetry
        }

        addItemCreator(repositoryCreator)
        repoViewModel.pagedList.observe(this, Observer {
            repositoryCreator.submitList(it)
           })
    }

    val POST_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.name == newItem.name

        override fun getChangePayload(oldItem: Repo, newItem: Repo): Any? {
           return newItem
        }
    }
}