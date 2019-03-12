package com.tangpj.repository.ui.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.recurve.resource.Resource
import com.tangpj.repository.repository.RepoRepository
import com.tangpj.repository.vo.RepoVo
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(private val repoRepository: RepoRepository): ViewModel(){

    private val _login = MutableLiveData<String>()


    private val starRepo: LiveData<Resource<List<RepoVo>>> = Transformations.switchMap(_login){
        repoRepository.loadRepos(it)
    }

    val repos: LiveData<List<RepoVo>> = Transformations.map(starRepo){
        resource.value = it
        it.data
    }

    val resource: MutableLiveData<Resource<*>> = MutableLiveData()

    fun setRepoOwner(login: String){
        _login.value = login
    }

}