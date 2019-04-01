package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.*
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.vo.RepoVo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repoVoList: List<RepoVo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserRepoResult(starRepoResultsList: List<StarRepoResult>)

    @Query(" SELECT * FROM RepoVo TEST WHERE id IN (:repoIds)")
    abstract fun loadRepoById(repoIds: List<String>): LiveData<List<RepoVo>>

    @Query("SELECT repoId FROM StarRepoResult WHERE login = :login ORDER BY starredAt DESC")
    abstract fun loadStarRepoResult(login: String): LiveData<List<String>>

    fun loadRepoOrderById(repoIds: List<String>): LiveData<List<RepoVo>>{
        val order = mutableMapOf<String, Int>()
        repoIds.withIndex().forEach {
            order[it.value] = it.index
        }
        return Transformations.map(loadRepoById(repoIds)) { repositories ->
            repositories.sortedBy {
                order[it.id]
            }
        }
    }
}