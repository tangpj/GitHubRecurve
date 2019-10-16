package com.tangpj.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.db.util.loadDataCovertMapById
import com.tangpj.repository.db.util.loadDataOrderById
import com.tangpj.repository.entity.domain.actor.git.Committer
import com.tangpj.repository.entity.domain.commit.Commit
import com.tangpj.repository.valueObject.result.CommitsResult
import com.tangpj.repository.vo.CommitVo

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
    abstract fun loadCommitteesByIds(ids: List<String>) : LiveData<List<Committer>>

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
        WHERE login = :login AND repoName = :repoName 
        AND startFirst = :startFirst 
        AND `after` = :after
        AND authorId = :authorId isnull
    """)
    abstract fun loadCommitResult(
            login: String?, repoName: String?, authorId: String?,
            startFirst: Int, after: String?) : LiveData<CommitsResult>

    @Suppress("MemberVisibilityCanBePrivate")
    fun loadCommitsOrderById(commitIds: List<String>): LiveData<List<Commit>>{
        return commitIds.loadDataOrderById{
            loadCommitsByIds(it)
        }
    }

    /**
     *
     * load committees group by id, If it is repeated, take the first one
     *
     * @method: loadCommitteesGroupById
     * @author: create by Tang
     * @createTime: 2019-09-10 19:03
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun loadCommitteesGroupById(committerIds: List<String>): LiveData<Map<String, Committer?>> =
        committerIds.loadDataCovertMapById {
            loadCommitteesByIds(ids = it)
        }


    /**
     *
     * Query the list of commits and query the list of committer based on the [Commit.committerId].
     * Finally combined into CommitVo
     *
     * @method: loadCommitVoListOrderById
     * @author: tangpengjian113
     * @createTime: 2019-09-10 19:27
     */
    fun loadCommitVoListOrderById(commitIds: List<String>) : LiveData<List<CommitVo>>{
        val commitsLiveData= loadCommitsOrderById(commitIds)
        val result = MediatorLiveData<List<CommitVo>>()
        result.addSource(commitsLiveData){ commits ->
            result.removeSource(commitsLiveData)
            val committerIds = commits.map { commit -> commit.committerId }
            result.addSource(loadCommitteesGroupById(committerIds)){ committerGroup ->
                result.value = commits.map { commit ->
                    val committerId = commit.committerId
                    CommitVo(
                            commit = commit,
                            committer = committerGroup[committerId] ?: Committer(committerId) ) }
            }
        }
        return result
    }

}