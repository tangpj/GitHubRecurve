package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.*
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.vo.Repo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repoList: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserRepoResult(starRepoResult: StarRepoResult)

    @Query("SELECT * FROM Repo TEST WHERE id IN (:repoIds)")
    abstract fun loadRepoById(repoIds: List<String>): LiveData<List<Repo>>

    @Query("SELECT * FROM StarRepoResult WHERE login = :login")
    abstract fun loadStarRepoResult(login: String): LiveData<StarRepoResult?>


    fun loadRepoOrderById(repoIds: List<String>): LiveData<List<Repo>>{
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