package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.valueObject.query.FileContentQuery
import com.tangpj.repository.valueObject.result.FileContentResult
import com.tangpj.repository.vo.FileContent

@Dao
abstract class RepoDetailDao{

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



}