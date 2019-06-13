package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.valueObject.FileContentResult
import com.tangpj.repository.valueObject.Owner
import com.tangpj.repository.valueObject.StarRepoId
import com.tangpj.repository.valueObject.StarRepoResult
import com.tangpj.repository.vo.FileContent
import com.tangpj.repository.vo.Repo


@Database(
        entities = [
            Repo::class,
            Owner::class,
            StarRepoId::class,
            StarRepoResult::class,
            FileContent::class,
            FileContentResult::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao() : RepoDao

    abstract fun repoDeatilDao() : RepoDetailDao

}