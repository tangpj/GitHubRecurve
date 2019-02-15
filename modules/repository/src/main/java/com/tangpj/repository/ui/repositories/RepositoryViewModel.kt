package com.tangpj.repository.ui.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.repository.repository.RepoRepository
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(private val repoRepository: RepoRepository): ViewModel(){

    private val _login: MutableLiveData<String> = MutableLiveData()

    val starRepo = Transformations.switchMap(_login){
        repoRepository.loadRepos(it)
    }

    fun setRepoOwner(login: String){
        _login.value = login
    }

}