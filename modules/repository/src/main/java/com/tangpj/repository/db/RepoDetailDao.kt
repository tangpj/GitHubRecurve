package com.tangpj.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangpj.repository.vo.RepoFileContent

@Dao
abstract class RepoDetailDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFileContent(repoFileContent: RepoFileContent)

    @Query( "SELECT * FROM RepoFileContent WHERE id = :id")
    abstract fun loadFileContentById(id: String)


    @Query( """
        SELECT * FROM RepoFileContent
        WHERE owner = :owner AND repoName = :repoName AND expression = :expression""")
    abstract fun loadFileContentByRepo(owner: String, repoName: String, expression: String):
            LiveData<RepoFileContent>


}