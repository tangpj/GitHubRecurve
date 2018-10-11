package com.tangpj.github.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.tangpj.github.pojo.GithubToken

@Dao
abstract class GithubTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(githubToken: GithubToken)

    @Query("""
        SELECT * FROM GithubToken WHERE id = :id
        """)
    abstract fun loadToken(id: Long = 0): LiveData<GithubToken>

    @Delete
    abstract fun deleteToken(githubToken: GithubToken)
}