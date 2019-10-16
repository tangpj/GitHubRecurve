package com.tangpj.repository.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 *
 * branch control
 *
 * @className: [BranchViewModel]
 * @author: tangpengjian113
 * @createTime: 2019/10/16 2:04 PM
 */
class BranchViewModel @Inject constructor(): ViewModel(){

    private val _branch = MutableLiveData<String>()

    //禁止外部修改
    val currentBranch: LiveData<String> = Transformations.map(_branch){
        it
    }

    fun changeBranch(branch: String){
        if (this._branch.value != branch){
            this._branch.value = branch
        }
    }

}