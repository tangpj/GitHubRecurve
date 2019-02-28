package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.vo.Owner
import com.tangpj.repository.vo.Repo


@Database(
        entities = [
            Repo::class,
            Owner::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao(): RepoDao

}