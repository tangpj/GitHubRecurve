package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.valueObject.FileContentResult
import com.tangpj.repository.vo.FileContent

@Dao
abstract class RepoDetailDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFileContent(repoFileContent: FileContent)

    @Query("SELECT * FROM FileContent WHERE id = :id")
    abstract fun loadFileContentById(id: String): LiveData<FileContent>


    @Query("""
        SELECT * FROM FileContent
        WHERE id = :id""")
    abstract fun loadFileContentByRepo(id: String): LiveData<FileContent>

    @Query("""
        SELECT * FROM FileContentResult
        WHERE owner = :owner AND repoName = :repoName AND expression = :expression
    """)
    abstract fun loadFileContentResult(owner: String, repoName: String, expression: String): LiveData<FileContentResult>

}