package com.tangpj.repository.ui.detail.fileContent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.repository.repository.FileContentRepository
import com.tangpj.repository.valueObject.query.FileContentQuery
import org.markdown4j.Markdown4jProcessor
import javax.inject.Inject

class FileContentViewModel @Inject constructor(private val fileContentRepository: FileContentRepository) : ViewModel(){

    private val _fileContentQuery = MutableLiveData<FileContentQuery>()

    val fileContent = Transformations.switchMap(_fileContentQuery){
        fileContentRepository.loadFileContent(it)
    }

    val html =  Transformations.map(fileContent){
        if ("md" == it.data?.fileExtensions){
            Markdown4jProcessor().process(it.data?.content)
        }else{
            it.data?.content
        }
    }

    fun loadFinleContentByQuery(fileContentQuery: FileContentQuery){
        _fileContentQuery.postValue(fileContentQuery)
    }

}