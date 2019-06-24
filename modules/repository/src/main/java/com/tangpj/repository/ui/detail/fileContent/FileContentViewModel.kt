package com.tangpj.repository.ui.detail.fileContent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.repository.repository.FileContentRepository
import com.tangpj.repository.valueObject.query.FileContentQuery
import javax.inject.Inject

class FileContentViewModel @Inject constructor(private val fileContentRepository: FileContentRepository) : ViewModel(){

    private val _fileContentQuery = MutableLiveData<FileContentQuery>()

    private val fileContent = Transformations.map(_fileContentQuery){
        fileContentRepository.loadFileContent(it)
    }

    fun loadFinleContentByQuery(fileContentQuery: FileContentQuery){
        _fileContentQuery.postValue(fileContentQuery)
    }

}