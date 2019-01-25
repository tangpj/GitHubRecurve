package com.tangpj.repository.ui.repositories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment
import com.tangpj.repository.databinding.FragmentBaseListBinding
import com.tangpj.repository.vo.Repository
import javax.inject.Inject

class RepositoriesFragment: RecurveDaggerListFragment() {

    @Inject
    lateinit var repositoryCreator: RepositoryCreator


    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding {
        val binding = FragmentBaseListBinding.inflate(inflater, container, false)
        initViewRecyclerView(binding.rv)
        initViewBinding(binding)
        return binding
    }

    private fun initViewBinding(binding: FragmentBaseListBinding){
        addItemCreator(repositoryCreator)
        repositoryCreator.addItem(Repository("Recurve"))
        repositoryCreator.addItem(Repository("Recurve1"))
    }
}