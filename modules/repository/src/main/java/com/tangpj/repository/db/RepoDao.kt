package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.github.domain.RepoFlag
import com.tangpj.repository.vo.RepoVo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(vararg repoVos: RepoVo)

    @Query("""
        SELECT * FROM RepoVo
        WHERE id in (:repoIds)
        ORDER BY stars DESC""")
    abstract fun loadRepositories(repoIds: List<Int>): LiveData<List<RepoVo>>

    @Query("SELECT repoId FROM UserRepoResult WHERE login = :login AND type = :type")
    abstract fun loadUserRepoResult(login: String, @RepoFlag type: Int): List<Int>
}