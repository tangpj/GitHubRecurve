package com.tangpj.repository.ui.repositories

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.tangpj.paging.Listing
import com.tangpj.paging.PageLoadState
import com.tangpj.recurve.resource.Resource
import com.tangpj.repository.repository.RepoRepository
import com.tangpj.repository.vo.RepoVo
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(private val repoRepository: RepoRepository): ViewModel(){

    private val _login = MutableLiveData<String>()

    var repoRetry: (() -> Unit)? = null

    var refresh: (() -> Unit)? = null

    private val repoListing: LiveData<Listing<RepoVo>> = Transformations.map(_login){
        repoRepository.loadStarRepos(it)
    }

    val repos = Transformations.switchMap(repoListing){
        repoRetry = it.retry
        refresh = it.refresh
        it.pagedList
    }


    val pageLoadState = Transformations.switchMap(repoListing){
        it.pageLoadState
    }


    fun setRepoOwner(login: String){
        _login.value = login
    }


}