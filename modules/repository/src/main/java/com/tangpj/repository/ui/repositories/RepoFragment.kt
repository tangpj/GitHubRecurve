package com.tangpj.repository.ui.repositories


import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import com.tangpj.repository.vo.Repository
import javax.inject.Inject

class RepoFragment: RecurveDaggerListFragment() {

    @Inject
    lateinit var repositoryCreator: RepositoryCreator

    override fun initRecyclerView(rv: RecyclerView) {
        super.initRecyclerView(rv)
        addItemCreator(repositoryCreator)
        repositoryCreator.addItem(Repository("Recurve"))
        repositoryCreator.addItem(Repository("Recurve1"))
    }


}