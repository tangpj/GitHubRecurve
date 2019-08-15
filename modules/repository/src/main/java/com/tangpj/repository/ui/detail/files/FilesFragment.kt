package com.tangpj.repository.ui.detail.files

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.FileItemCreator
import com.tangpj.repository.valueObject.query.GitObjectQuery
import javax.inject.Inject

class FilesFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var filesViewModel: FilesViewModel

    lateinit var fileItemCreator: FileItemCreator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
