package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.domain.Owner
import com.tangpj.repository.domain.StarRepoId
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.vo.RepoFileContent
import com.tangpj.repository.vo.Repo


@Database(
        entities = [
            Repo::class,
            Owner::class,
            StarRepoId::class,
            StarRepoResult::class,
            RepoFileContent::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao() : RepoDao

    abstract fun repoDeatilDao() : RepoDetailDao

}