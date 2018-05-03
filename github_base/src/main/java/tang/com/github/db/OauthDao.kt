package tang.com.github.db

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import tang.com.github.pojo.GithubToken

interface OauthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubToken: GithubToken)

    @Query("SELECT * FROM githubToken ")
    fun findByAccount()
}