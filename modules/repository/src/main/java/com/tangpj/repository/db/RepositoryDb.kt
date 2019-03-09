package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.domain.OwnerDo
import com.tangpj.repository.domain.UserRepoResult
import com.tangpj.repository.vo.RepoVo


@Database(
        entities = [
            RepoVo::class,
            OwnerDo::class,
            UserRepoResult::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao(): RepoDao


}