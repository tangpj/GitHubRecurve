package com.tangpj.github.valueObject

import androidx.lifecycle.LiveData
import com.tangpj.github.utils.AbsentLiveData

interface Query<Child>{
    fun <T> ifExists(f: (Child) -> LiveData<T>) : LiveData<T> = AbsentLiveData.create()
}