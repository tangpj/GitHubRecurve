package com.tangpj.repository.ui.repositories

import android.os.Bundle
import com.tangpj.github.BaseListActivity
import com.tangpj.recurve.dagger2.RecurveDaggerListActivity
import com.tangpj.repository.R
import com.tangpj.repository.vo.Repository
import javax.inject.Inject

class RepositoriesActivity : BaseListActivity(){

    @Inject
    lateinit var repositoryCreator: RepositoryCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addItemCreator(repositoryCreator)

        appbar{
            title = getString(R.string.app_name)
        }
        repositoryCreator.addItem(Repository("Recurve"))
    }

}