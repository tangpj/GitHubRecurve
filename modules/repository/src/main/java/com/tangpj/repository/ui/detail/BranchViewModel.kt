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

    val currentBranch = MutableLiveData<String>()

    fun changeBranch(branch: String){
        if (this.currentBranch.value != branch){
            this.currentBranch.value = branch
        }
    }

}