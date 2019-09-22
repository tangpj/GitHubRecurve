package com.tangpj.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.db.util.loadDataOrderById
import com.tangpj.repository.entity.domain.Ref
import com.tangpj.repository.valueObject.query.Prefix
import com.tangpj.repository.valueObject.result.RefsResult

@Dao
abstract class RefDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRef(ref: Ref)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRefs(refs: List<Ref>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRefsResult(refsResult: RefsResult)

    @Query("""
        SELECT * FROM Ref
        WHERE id = :id
    """)
    abstract fun loadRef(id: String) : LiveData<Ref>

    @Query("""
        SELECT * FROM Ref
        TEST WHERE id IN (:ids)
    """)
    abstract fun loadRefByIds(ids: List<String>) : LiveData<List<Ref>>

    @Query("""
        SELECT * FROM RefsResult
        WHERE login = :login 
        AND repoName = :repoName
        AND prefix = :prefix
        AND startFirst = :startFirst
        AND `after` = :after
    """)
    abstract fun loadRefsResult(
            login: String,
            repoName: String,
            prefix: String,
            startFirst: Int,
            after: String? = "") : LiveData<RefsResult>

    fun loadRefsOrderById(refsIds: List<String>) =
            refsIds.loadDataOrderById {
                loadRefByIds(it)
            }
}