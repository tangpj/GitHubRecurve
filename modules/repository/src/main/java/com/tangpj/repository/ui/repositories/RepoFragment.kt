package com.tangpj.repository.ui.repositories


import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.recurve.resource.Resource
import com.tangpj.repository.creator.RepositoryCreator
import com.tangpj.repository.vo.RepoVo
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
            Timber.d("load status = ${it.status}; netState = ${it.networkState.status}")
            Timber.d(it.networkState.msg)
        }

        loading<RepoVo> {
            resource = repoViewModel.repoResource
            retry = repoViewModel.refresh
        }

        addItemCreator(repositoryCreator)
        repoViewModel.repoResource.observe(this, Observer {
            it?.data?.let { pageList ->
                repositoryCreator.submitList(pageList)
                Timber.d(pageList.size.toString())
            }
        })
    }

    val POST_COMPARATOR = object : DiffUtil.ItemCallback<RepoVo>() {
        override fun areContentsTheSame(oldItem: RepoVo, newItem: RepoVo): Boolean =
                oldItem == newItem

        override fun areItemsTheSame(oldItem: RepoVo, newItem: RepoVo): Boolean =
                oldItem.name == newItem.name

        override fun getChangePayload(oldItem: RepoVo, newItem: RepoVo): Any? {
           return newItem
        }
    }
}