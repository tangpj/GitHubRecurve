package com.tangpj.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangpj.github.BuildConfig
import com.tangpj.repository.db.dao.CommitDao
import com.tangpj.repository.db.dao.RefDao
import com.tangpj.repository.db.dao.RepoDao
import com.tangpj.repository.db.dao.RepoDetailDao
import com.tangpj.repository.entity.domain.Ref
import com.tangpj.repository.entity.domain.actor.Owner
import com.tangpj.repository.entity.domain.actor.git.Committer
import com.tangpj.repository.entity.domain.file.FileContent
import com.tangpj.repository.entity.domain.file.FileItem
import com.tangpj.repository.entity.domain.repo.Repo
import com.tangpj.repository.entity.domain.repo.RepoDetail
import com.tangpj.repository.entity.domain.commit.Commit
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
            Committer::class,
            Ref::class,
            RefsResult::class
        ],
        version = BuildConfig.DB_VERSION_GITHUB)
abstract class RepositoryDb: RoomDatabase(){

    abstract fun repoDao() : RepoDao

    abstract fun repoDetailDao() : RepoDetailDao

    abstract fun commitDao() : CommitDao

    abstract fun refDao() : RefDao
}