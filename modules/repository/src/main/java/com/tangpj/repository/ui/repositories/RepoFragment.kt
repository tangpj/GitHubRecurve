package com.tangpj.repository.ui.repositories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import com.tangpj.repository.creator.RepositoryCreator
import timber.log.Timber
import javax.inject.Inject

class RepoFragment: RecurveDaggerListFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var repoViewModel: RepositoryViewModel

    @Inject
    lateinit var repositoryCreator: RepositoryCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arg = RepoFragmentArgs.fromBundle(arguments)
        Timber.d("user name = ${arg.userName}")

        repoViewModel.repos.observeForever { repos ->
            repos?.let {
                repositoryCreator.addItems(it)
            }
        }
        repoViewModel.setRepoOwner("Tangpj")
    }

    override fun onCreateBinding(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding {
        repoViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepositoryViewModel::class.java)
        val binding = FragmentBaseRecyclerViewBinding.inflate(inflater, container, false)
        repoViewModel.resource.observeForever {
            Timber.d("${it.status}")
        }
        binding.resource = repoViewModel.resource
        binding.setLifecycleOwner(this)
        initRecyclerView(binding.recyclerContent.rv)
        return binding
    }

    override fun initRecyclerView(rv: RecyclerView) {
        super.initRecyclerView(rv)
        addItemCreator(repositoryCreator)

    }


}