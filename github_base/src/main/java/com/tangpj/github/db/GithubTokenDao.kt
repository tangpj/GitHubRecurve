package com.tangpj.github.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tangpj.github.pojo.GithubToken

@Dao
interface GithubTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubToken: GithubToken)


}