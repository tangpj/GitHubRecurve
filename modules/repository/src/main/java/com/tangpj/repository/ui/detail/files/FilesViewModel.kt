package com.tangpj.repository.ui.detail.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.recurve.core.resource.Resource
import com.tangpj.repository.repository.FileRepository
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.entity.domain.file.FileItem
import javax.inject.Inject

class FilesViewModel @Inject constructor(private val fileRepository: FileRepository) : ViewModel(){

    private val _fileTreeQuery = MutableLiveData<GitObjectQuery>()

    val fileItems: LiveData<Resource<List<FileItem>>> = Transformations.switchMap(_fileTreeQuery){
            fileRepository.loadFileDirectory(it)
    }

    fun loadFileTreeByQuery(fileTreeQuery : GitObjectQuery){
        val update = GitObjectQuery(fileTreeQuery .repoDetailQuery, fileTreeQuery .branch, fileTreeQuery .path)
        if (update != _fileTreeQuery.value){
            _fileTreeQuery.value = fileTreeQuery
        }
    }

    fun retry(){
        val fileTreeQuery = _fileTreeQuery.value
        fileTreeQuery ?: return
        _fileTreeQuery.value = GitObjectQuery(fileTreeQuery.repoDetailQuery, fileTreeQuery.branch, fileTreeQuery.path)

    }

}