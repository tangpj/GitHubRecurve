package com.tangpj.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.entity.author.CommitAuthor
import com.tangpj.repository.entity.commit.Commit
import com.tangpj.repository.valueObject.result.CommitsResult

@Dao
abstract class CommitDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommits(commits: List<Commit>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommitsResult(commitsResult: CommitsResult)

    @Query("""
        SELECT * FROM CommitsResult
        WHERE login = :login AND name = :name AND authorId = :authorId
    """)
    abstract fun loadCommit(login: String, name: String, authorId: String?) : LiveData<CommitsResult>

    @Query("""
        SELECT * FROM `Commit` 
        WHERE id = :id
    """)
    abstract fun loadCommitById(id: String) : LiveData<Commit>

    @Query("""
        SELECT * FROM `Commit`
        WHERE id IN (:ids)
    """)
    abstract fun loadCommitsByIds(ids: List<String>): LiveData<List<Commit>>
}