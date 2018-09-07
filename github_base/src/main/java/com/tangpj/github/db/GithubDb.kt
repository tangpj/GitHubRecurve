package com.tangpj.github.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.github.pojo.GithubToken

@Database(entities = [GithubToken::class],version = BuildConfig.DB_VERSION_GITHUB)
abstract class GithubDb: RoomDatabase() {

    abstract fun tokenDao(): GithubTokenDao
}