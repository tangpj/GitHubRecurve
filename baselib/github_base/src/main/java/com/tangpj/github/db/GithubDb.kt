package com.tangpj.github.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.github.pojo.GithubToken
import com.tangpj.github.vo.Owner
import com.tangpj.github.vo.Repo

@Database(
        entities = [
            GithubToken::class,
            Repo::class,
            Owner::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class GithubDb: RoomDatabase() {

    abstract fun tokenDao(): GithubTokenDao

    abstract fun repoDao(): RepoDao
}