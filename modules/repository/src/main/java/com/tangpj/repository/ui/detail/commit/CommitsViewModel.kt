package com.tangpj.repository.ui.detail.commit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
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

    val pageList: LiveData<PagedList<CommitVo>> = Transformations.switchMap(commitsListing){
        it.pagedList
    }

    fun setCommitQuery(commitsQuery: CommitsQuery){
        _commitQuery.value = commitsQuery
    }

}