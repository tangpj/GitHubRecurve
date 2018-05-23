package tang.com.github.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import tang.com.github.pojo.GithubToken

@Dao
interface GithubTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubToken: GithubToken)


}