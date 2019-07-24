package com.tangpj.repository.ui.detail.files

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.FileItemCreator
import com.tangpj.repository.valueObject.query.GitObjectQuery
import javax.inject.Inject

private const val KEY_FILES_QUERY =
        "com.tangpj.repository.ui.detail.files.KEY_FILES_QUERY"

class FilesFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var filesViewModel: FilesViewModel

    lateinit var fileItemCreator: FileItemCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arg = FilesFragment

    }

    companion object{
        fun create(query: GitObjectQuery) =
                FilesFragment().apply {
                    arguments = Bundle(1).apply {
                        putParcelable(KEY_FILES_QUERY, query)
                    }
                }
    }

    override fun onBindingInit(binding: ViewDataBinding) {

    }


}
