package com.tangpj.github.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tangpj.github.vo.Repo

@Dao
abstract class RepoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repo: Repo)

}