package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.github.domain.RepoFlag
import com.tangpj.repository.domain.UserRepoResult
import com.tangpj.repository.vo.RepoVo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repoVoList: List<RepoVo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserRepoResult(userRepoResultsList: List<UserRepoResult>)

    @Query("""
        SELECT * FROM RepoVo
        WHERE id in (:repoIds)
        ORDER BY stars DESC""")
    abstract fun loadRepositories(repoIds: List<String>): LiveData<List<RepoVo>>

    @Query("SELECT repoId FROM UserRepoResult WHERE login = :login AND type = :type")
    abstract fun loadUserRepoResult(login: String, @RepoFlag type: Int): LiveData<List<String>>
}