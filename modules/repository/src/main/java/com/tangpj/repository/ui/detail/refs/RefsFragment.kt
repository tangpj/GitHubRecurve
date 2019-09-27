package com.tangpj.repository.ui.detail.refs

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tangpj.github.ui.ModulePagingFragment
import com.tangpj.repository.ui.creator.RefCreator
import javax.inject.Inject

/**
 *
 * Fetch a list of refs from the repository
 *
 * @className: RefsFragment
 * @author create by Tang
 * @date  12:44 PM
 */

class RefsFragment : ModulePagingFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var refsViewModel: RefsViewModel

    @Inject
    lateinit var refCreator: RefCreator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
        }
    }


}