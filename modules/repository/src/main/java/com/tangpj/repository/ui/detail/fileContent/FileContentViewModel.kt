package com.tangpj.repository.ui.detail.fileContent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.recurve.resource.Resource
import com.tangpj.repository.repository.FileContentRepository
import com.tangpj.repository.valueObject.query.FileContentQuery
import com.tangpj.repository.vo.FileContent
import javax.inject.Inject

class FileContentViewModel @Inject constructor(private val fileContentRepository: FileContentRepository) : ViewModel(){

    private val _fileContentQuery = MutableLiveData<FileContentQuery>()

    val fileContent: LiveData<Resource<FileContent>>
            = Transformations.switchMap(_fileContentQuery){
        it.ifExists { fileContentQuery->
            fileContentRepository.loadFileContent(fileContentQuery)
        }
    }

    fun loadFileContentByQuery(fileContentQuery: FileContentQuery){
        val update = FileContentQuery(fileContentQuery.repoDetailQuery, fileContentQuery.expression)
        if (update == _fileContentQuery.value){
            return
        }
        _fileContentQuery.value = update
    }

    fun retry(){
        val fileContentQuery = _fileContentQuery.value
        fileContentQuery ?: return
        _fileContentQuery.value = FileContentQuery(fileContentQuery.repoDetailQuery, fileContentQuery.expression)
    }

}