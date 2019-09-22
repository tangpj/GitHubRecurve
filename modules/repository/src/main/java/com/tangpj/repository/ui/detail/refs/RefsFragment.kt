package com.tangpj.repository.ui.detail.refs

import androidx.lifecycle.ViewModelProvider
import com.tangpj.github.ui.ModulePagingFragment
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


}