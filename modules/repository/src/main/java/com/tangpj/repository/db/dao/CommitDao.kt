package com.tangpj.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.db.util.loadDataOrderByMe
import com.tangpj.repository.entity.actor.git.Committer
import com.tangpj.repository.entity.author.CommitAuthor
import com.tangpj.repository.entity.commit.Commit
import com.tangpj.repository.valueObject.result.CommitsResult

@Dao
abstract class CommitDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommit(commit: Commit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommits(commits: List<Commit>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommitsResult(commitsResult: CommitsResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommitter(committer: Committer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommittees(committees: List<Committer>)

    @Query("""
        SELECT * FROM CommitsResult
        WHERE login = :login AND repoName = :repoName AND authorId = :authorId
    """)
    abstract fun loadCommit(login: String, repoName: String, authorId: String?) : LiveData<CommitsResult>

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

    @Query("""
        SELECT * FROM Committer
        WHERE id = :id
    """)
    abstract fun loadCommitter(id: String) : LiveData<Committer>

    @Query("""
        SELECT * FROM Committer
        WHERE id IN (:ids)
    """)
    abstract fun loadCommittees(ids: List<String>) : LiveData<List<Committer>>

    /**
     *
     * load all when [authorId] is empty
     *
     * @method:loadCommitResult
     * @author create by Tang
     * @date 2019-09-09 23:21
     */
    @Query("""
        SELECT * FROM CommitsResult
        WHERE login = :login AND repoName = :repoName AND authorId = :authorId
    """)
    abstract fun loadCommitResult(login: String, repoName: String, authorId: String?) : LiveData<CommitsResult>

    fun loadCommitsOrderById(commitIds: List<String>): LiveData<List<Commit>>{
        return commitIds.loadDataOrderByMe {
            loadCommitsByIds(it)
        }
    }

}