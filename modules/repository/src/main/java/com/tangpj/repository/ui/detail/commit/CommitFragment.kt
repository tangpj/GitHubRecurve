package com.tangpj.repository.ui.detail.commit

import androidx.lifecycle.ViewModelProvider
import com.tangpj.github.ui.ModulePagingFragment
import javax.inject.Inject

class CommitFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var commitsViewModel: CommitsViewModel


}


