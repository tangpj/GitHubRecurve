package com.tangpj.github.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.github.pojo.GithubToken

@Dao
abstract class GithubTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(githubToken: GithubToken)

    @Query("""
        SELECT * FROM GithubToken WHERE :code
        """)
    abstract fun loadToken(code: String): LiveData<GithubToken>
}