package com.tangpj.repository.ui.detail.refs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.tangpj.paging.Listing
import com.tangpj.repository.entity.domain.Ref
import com.tangpj.repository.repository.RefsRepository
import com.tangpj.repository.valueObject.query.RefsQuery
import javax.inject.Inject

class RefsViewModel @Inject constructor(
        private val refsRepository: RefsRepository) : ViewModel() {

    private val _refsQuery = MutableLiveData<RefsQuery>()

    val  refListing: LiveData<Listing<Ref>> = Transformations.switchMap(_refsQuery){ query ->
        query.ifExists {
            refsRepository.loadRefs(it)
        }
    }

    val pageList: LiveData<PagedList<Ref>> = Transformations.switchMap(refListing){
        it.pagedList
    }

    fun setRefsQuery(refsQuery: RefsQuery){
        _refsQuery.value = refsQuery
    }

}