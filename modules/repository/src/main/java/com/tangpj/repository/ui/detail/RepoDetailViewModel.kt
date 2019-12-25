package com.tangpj.repository.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.recurve.core.resource.Resource
import com.tangpj.repository.entity.domain.repo.RepoDetail
import com.tangpj.repository.repository.RepoDetailRepository
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(private val repoDetailRepository: RepoDetailRepository)
    : ViewModel(){

    private val _repoDetailQuery = MutableLiveData<RepoDetailQuery>()

    val repoDetail: LiveData<Resource<RepoDetail>> = Transformations.switchMap(_repoDetailQuery){
        repoDetailQuery ->
            repoDetailRepository.loadRepoDetail(repoDetailQuery)
    }

    fun loadRepoDetail(login: String, name: String){
        val update = RepoDetailQuery(login = login, name = name)
        if (update == _repoDetailQuery.value){
            return
        }
        _repoDetailQuery.value = update
    }

    fun retry(){
        val repositoriesQuery = _repoDetailQuery.value
        repositoriesQuery ?: return
        _repoDetailQuery.value = repositoriesQuery
    }
}