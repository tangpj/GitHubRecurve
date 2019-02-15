package com.tangpj.github.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.github.vo.Repo

@Dao
abstract class RepoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(verepo: List<Repo>)

    @Query("""
        SELECT * FROM REPO
        WHERE owner_login = :owner
        ORDER BY stars DESC""")
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>
}