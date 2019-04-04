package com.tangpj.oauth2.db


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import com.tangpj.oauth2.domain.GithubToken

@Dao
abstract class GithubTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(githubToken: GithubToken)

    @Query(" SELECT * FROM github_token WHERE id = :id ")
    abstract fun loadToken(id: Long = 0): LiveData<GithubToken>


    //不需要监听生命周期，并且非UI线程调用
    @WorkerThread
    @Query(" SELECT * FROM github_token WHERE id = :id ")
    abstract fun loadTokenForIO(id: Long = 0): GithubToken?

    @Delete
    abstract fun deleteToken(githubToken: GithubToken)
}