package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.*
import com.tangpj.repository.domain.StarRepoResult
import com.tangpj.repository.vo.Repo

/**
 *
 * Github资源库数据库操作类
 * [Repo] 只包含简单的资源库信息，用于资源库列表显示
 * 如果需要获取更多信息的话，则需要使用[RepoDetailDao]
 *
 * @className: RepoDao
 * @author create by Tang
 * @date  17:27
 */
@Dao
abstract class RepoDao{

    /**
     *
     * 插入资源库列表
     *
     * @method: insertRepos
     * @author create by Tang
     * @date 2019-06-09 17:27
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repoList: List<Repo>)

    /**
     *
     * 插入用户Star资源库列表
     *
     * @method: insertUserRepoResult
     * @author create by Tang
     * @date 2019-06-09 17:28
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserRepoResult(starRepoResult: StarRepoResult)

    /**
     *
     * 根据仓库Id加载资源库
     *
     * @method: loadRepoById
     * @author create by Tang
     * @date 2019-06-09 17:28
     */
    @Query("SELECT * FROM Repo TEST WHERE id IN (:repoIds)")
    abstract fun loadRepoById(repoIds: List<String>): LiveData<List<Repo>>

    /**
     * 根据用户加载用户Star的资源库
     *
     * @method: loadStarRepoResult
     * @author create by Tang
     * @date 2019-06-09 17:29
     */
    @Query("SELECT * FROM StarRepoResult WHERE login = :login")
    abstract fun loadStarRepoResult(login: String): LiveData<StarRepoResult?>

    fun loadRepoOrderById(repoIds: List<String>): LiveData<List<Repo>>{
        val order = mutableMapOf<String, Int>()
        repoIds.withIndex().forEach {
            order[it.value] = it.index
        }
        return Transformations.map(loadRepoById(repoIds)) { repositories ->
            repositories.sortedBy {
                order[it.id]
            }
        }
    }
}