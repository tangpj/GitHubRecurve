package com.tangpj.repository.ui.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.recurve.resource.Resource
import com.tangpj.repository.repository.RepoRepository
import com.tangpj.repository.vo.RepoVo
import timber.log.Timber
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(private val repoRepository: RepoRepository): ViewModel(){

    private val _login = MutableLiveData<String>()

    private val starRepo: LiveData<Resource<List<RepoVo>>> = Transformations.switchMap(_login){
        Timber.d(it)
        repoRepository.loadRepos(it)
    }

    val repos: LiveData<List<RepoVo>> = Transformations.map(starRepo){
        val dateEmpty = it?.data?.isEmpty()
        val resourceValue = if (dateEmpty != null && dateEmpty){
            Resource(it.status, null, it.message)
        }else{
            it
        }

        resource.postValue(resourceValue)
        it.data
    }

    val resource: MutableLiveData<Resource<*>> = MutableLiveData()

    val retry: () -> Unit = {
        _login.value = _login.value
    }

    fun setRepoOwner(login: String){
        _login.value = login
    }

}