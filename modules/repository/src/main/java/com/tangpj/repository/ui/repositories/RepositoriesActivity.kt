package com.tangpj.repository.ui.repositories

import android.os.Bundle
import com.tangpj.recurve.dagger2.RecurveDaggerListActivity
import javax.inject.Inject

class RepositoriesActivity : RecurveDaggerListActivity(){

    @Inject
    lateinit var repositoryCreator: RepositoryCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addItemCreator(repositoryCreator)
    }

}