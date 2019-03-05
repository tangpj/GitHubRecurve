package com.tangpj.repository.ui.repositories


import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
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
        repoViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepositoryViewModel::class.java)
        repoViewModel.starRepo.observeForever {
            it.data?.let { items ->
                repositoryCreator.addItems(items)
            }
        }
        repoViewModel.setRepoOwner("Tangpj")
    }

    override fun initRecyclerView(rv: RecyclerView) {
        super.initRecyclerView(rv)
        addItemCreator(repositoryCreator)


    }


}