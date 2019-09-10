package com.tangpj.repository.ui.detail.commit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tangpj.paging.Listing
import com.tangpj.repository.repository.CommitRepository
import com.tangpj.repository.valueObject.query.CommitsQuery
import com.tangpj.repository.vo.CommitVo
import javax.inject.Inject

class CommitsViewModel @Inject constructor(private val commitRepository: CommitRepository) : ViewModel(){

    private val _commitQuery = MutableLiveData<CommitsQuery>()

    val commitsListing: LiveData<Listing<CommitVo>> = Transformations.switchMap(_commitQuery){ query ->
        query.ifExists {
            commitRepository.loadCommits(it)
        }
    }

    val pageList = Transformations.switchMap(commitsListing){
        it.pagedList
    }

    val commitRetry = Transformations.map(commitsListing){
        it.retry
    }

    val refresh = Transformations.map(commitsListing){
        it.refresh
    }

    val pageLoadState = Transformations.switchMap(commitsListing){
        it?.pageLoadState
    }

    fun setCommitQuery(commitsQuery: CommitsQuery){
        _commitQuery.value = commitsQuery
    }

}