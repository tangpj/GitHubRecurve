package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.valueObject.result.FileContentResult
import com.tangpj.repository.valueObject.Owner
import com.tangpj.repository.valueObject.result.FileItemsResult
import com.tangpj.repository.valueObject.result.StarRepoId
import com.tangpj.repository.valueObject.result.StarRepoResult
import com.tangpj.repository.entry.vo.FileContent
import com.tangpj.repository.entry.vo.FileItem
import com.tangpj.repository.entry.vo.Repo
import com.tangpj.repository.entry.vo.RepoDetail


@Database(
        entities = [
            Repo::class,
            RepoDetail::class,
            Owner::class,
            StarRepoId::class,
            StarRepoResult::class,
            FileItem::class,
            FileItemsResult::class,
            FileContent::class,
            FileContentResult::class],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao() : RepoDao

    abstract fun repoDetailDao() : RepoDetailDao

}