package com.tangpj.repository.ui.repositories

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.recurve.paging.Listing
import com.recurve.paging.PageLoadState
import com.tangpj.repository.repository.RepoRepository
import com.tangpj.repository.entity.domain.repo.Repo
import javax.inject.Inject

class ReposViewModel @Inject constructor(private val repoRepository: RepoRepository): ViewModel(){

    private val _login = MutableLiveData<String>()

    val repoListing: LiveData<Listing<Repo>> = Transformations.switchMap(_login){
        repoRepository.loadStarRepos(it)
    }


    val pagedList: LiveData<PagedList<Repo>> = Transformations.switchMap(repoListing){
        it.pagedList
    }

    val repoRetry: LiveData<() -> Unit>  = Transformations.map(repoListing){
        it.retry
    }

    var refresh: LiveData<() -> Unit> = Transformations.map(repoListing){
        it.refresh
    }
    val pageLoadState: LiveData<PageLoadState> = Transformations.switchMap(repoListing) {
        it?.pageLoadState
    }

    fun setRepoOwner(login: String){
        _login.value = login
    }


}