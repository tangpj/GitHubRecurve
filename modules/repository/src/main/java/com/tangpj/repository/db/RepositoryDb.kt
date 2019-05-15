package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.domain.Owner
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.vo.RepoVo


@Database(
        entities = [
            RepoVo::class,
            Owner::class,
            StarRepoResult::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao(): RepoDao


}