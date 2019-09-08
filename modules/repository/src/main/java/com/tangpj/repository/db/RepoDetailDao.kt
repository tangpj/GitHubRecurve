package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.db.util.loadDataOrderByMe
import com.tangpj.repository.valueObject.result.FileContentResult
import com.tangpj.repository.valueObject.result.FileItemsResult
import com.tangpj.repository.entry.file.FileContent
import com.tangpj.repository.entry.file.FileItem
import com.tangpj.repository.entry.vo.RepoDetail

@Dao
abstract class RepoDetailDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepoDetail(repoDetail: RepoDetail)

    /**
     *
     * File dir
     *
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFileItems(fileItems: List<FileItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFileItemResult(fileItemsResult: FileItemsResult)

    @Query("""
        SELECT * FROM RepoDetail
        WHERE owner_login = :login AND name = :name
    """)
    abstract fun loadRepoDetail(login: String, name: String): LiveData<RepoDetail>

    @Query("""
        SELECT * FROM FileItem 
        WHERE id IN (:ids)
    """)
    abstract fun loadFileItemsById(ids: List<String>): LiveData<List<FileItem>>

    @Query("""
        SELECT * FROM FileItemsResult
        WHERE owner = :owner AND repoName = :name AND expression = :expression
    """)
    abstract fun loadFileItemsResult(owner: String, name: String, expression: String): LiveData<FileItemsResult>

    /**
     *
     * FileContent
     *
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFileContent(fileContent: FileContent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFileContentResult(fileContentResult: FileContentResult)

    @Query("SELECT * FROM FileContent WHERE id = :id")
    abstract fun loadFileContentById(id: String): LiveData<FileContent>

    @Query("""
        SELECT * FROM FileContentResult
        WHERE owner = :owner AND repoName = :name AND expression = :expression
    """)
    abstract fun loadFileContentResult(owner: String, name: String, expression: String): LiveData<FileContentResult>


    fun loadFileItemOrderById(ids: List<String>): LiveData<List<FileItem>>{
        return ids.loadDataOrderByMe{
            loadFileItemsById(it)
        }
    }





}