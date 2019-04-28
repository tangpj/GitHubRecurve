package com.tangpj.repository.ui.repositories

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.tangpj.paging.Listing
import com.tangpj.paging.PageLoadState
import com.tangpj.recurve.resource.NetworkState
import com.tangpj.recurve.resource.Resource
import com.tangpj.repository.repository.RepoRepository
import com.tangpj.repository.vo.RepoVo
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(private val repoRepository: RepoRepository): ViewModel(){

    private val _login = MutableLiveData<String>()

    val pageLoadState = MediatorLiveData<PageLoadState>()

    var repoRetry: (() -> Unit)? = null

    private val repoResource: LiveData<Listing<RepoVo>> = Transformations.map(_login){
        repoRepository.loadStarRepos(it)
    }

    val repos: LiveData<PagedList<RepoVo>> = Transformations.switchMap(repoResource){
        pageLoadState.addSource(it.networkState){ state ->
            pageLoadState.postValue(state)
        }
        repoRetry = it.retry
        it?.pagedList
    }

    fun setRepoOwner(login: String){
        _login.value = login
    }


}