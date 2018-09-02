package tang.com.github.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tang.com.github.BuildConfig
import tang.com.github.pojo.GithubToken

@Database(entities = [GithubToken::class],version = BuildConfig.DB_VERSION_GITHUB)
abstract class GithubDb: RoomDatabase() {

    abstract fun oauthDao(): GithubTokenDao
}