package com.tangpj.repository.ui.repositories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import com.tangpj.repository.creator.RepositoryCreator
import timber.log.Timber
import javax.inject.Inject

class RepoFragment: ModulePagingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var repoViewModel: RepositoryViewModel

    lateinit var repositoryCreator : RepositoryCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arg = RepoFragmentArgs.fromBundle(arguments)
        Timber.d("user name = ${arg.login}")
        repoViewModel.setRepoOwner(arg.login)
    }

    override fun onBindingInit(binding: ViewDataBinding) {
        repoViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepositoryViewModel::class.java)
        repositoryCreator = RepositoryCreator(adapter)
        repoViewModel.resource.observeForever {
            Timber.d("${it.status}")
            Timber.d("${it.message}")
        }

        loading {
            resource = repoViewModel.resource
            retry = {
            }
        }
        repoViewModel.repos.observeForever { repoVoList ->
            repoVoList?.let {
                if (repositoryCreator.getData().isEmpty())
                    repositoryCreator.setDataList(it)
            }
        }
        addItemCreator(repositoryCreator)
    }
}