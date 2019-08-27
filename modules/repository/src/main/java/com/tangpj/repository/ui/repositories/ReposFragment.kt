package com.tangpj.repository.ui.repositories


import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.ui.creator.RepositoryCreator
import com.tangpj.repository.ui.detail.KEY_REPO_DETAIL_QUERY
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.repository.entry.vo.Repo
import timber.log.Timber
import javax.inject.Inject

class ReposFragment: ModulePagingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoViewModel: ReposViewModel

    private lateinit var repositoryCreator : RepositoryCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            val arg = ReposFragmentArgs.fromBundle(it)
            Timber.d("user name = ${arg.login}")
            repoViewModel.setRepoOwner(arg.login)
        }
    }

    override fun onBindingInit(binding: ViewDataBinding) {
        repoViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ReposViewModel::class.java)
        repositoryCreator = RepositoryCreator(POST_COMPARATOR)
        pagedLoading<Repo> {
            listing = repoViewModel.repoListing
        }

        addItemCreator(repositoryCreator)
        repositoryCreator.setOnItemClickListener { _ , e, _ ->
            val repoDetailQuery = RepoDetailQuery(login = e.owner.login, name = e.name)
            ARouter.getInstance().build(PATH_REPO_DETAILS).withParcelable(KEY_REPO_DETAIL_QUERY, repoDetailQuery).navigation()
        }
        repoViewModel.pagedList.observe(this, Observer {
            repositoryCreator.submitList(it)
           })
    }

    private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.name == newItem.name

        override fun getChangePayload(oldItem: Repo, newItem: Repo): Any? {
           return newItem
        }
    }
}