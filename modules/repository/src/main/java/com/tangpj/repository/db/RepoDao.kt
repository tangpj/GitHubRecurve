package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.github.domain.RepoFlag
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.vo.RepoVo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repoVoList: List<RepoVo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserRepoResult(starRepoResultsList: List<StarRepoResult>)

    @Query(" SELECT * FROM RepoVo WHERE id IN (:repoIds)")
    abstract fun loadRepositories(repoIds: List<String>): LiveData<List<RepoVo>>

    @Query("SELECT repoId FROM StarRepoResult WHERE login = :login ")
    abstract fun loadUserRepoResult(login: String): LiveData<List<String>>
}