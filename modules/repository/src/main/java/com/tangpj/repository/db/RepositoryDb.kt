package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.db.dao.CommitDao
import com.tangpj.repository.db.dao.RepoDao
import com.tangpj.repository.db.dao.RepoDetailDao
import com.tangpj.repository.entity.actor.Owner
import com.tangpj.repository.entity.actor.git.Committer
import com.tangpj.repository.entity.file.FileContent
import com.tangpj.repository.entity.file.FileItem
import com.tangpj.repository.vo.Repo
import com.tangpj.repository.vo.RepoDetail
import com.tangpj.repository.entity.commit.Commit
import com.tangpj.repository.valueObject.result.*


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
            FileContentResult::class,
            Commit::class,
            CommitsResult::class,
            Committer::class
        ],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao() : RepoDao

    abstract fun repoDetailDao() : RepoDetailDao

    abstract fun commitDao() : CommitDao
}