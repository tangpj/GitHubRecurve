package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.vo.RepoVo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(vararg repoVos: RepoVo)

    @Query("""
        SELECT * FROM RepoVo
        WHERE owner_login = :owner
        ORDER BY stars DESC""")
    abstract fun loadRepositories(owner: String): LiveData<List<RepoVo>>
}