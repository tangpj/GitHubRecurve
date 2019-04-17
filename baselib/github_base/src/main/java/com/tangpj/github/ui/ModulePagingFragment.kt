package com.tangpj.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.adapter.creator.Creator
import com.tangpj.github.databinding.FragmentBaseRecyclerViewBinding
import com.tangpj.recurve.dagger2.RecurveDaggerListFragment

/**
 *
 * @className: 模块化Paging
 * @author: tangpengjian113
 * @createTime: 2019-04-15 15:45
 */
class ModulePagingFragment: RecurveDaggerListFragment(){

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding? {
        return super.onCreateBinding(inflater, container, savedInstanceState)

    }

    override fun addItemCreator(creator: Creator) {
        super.addItemCreator(creator)
    }
}