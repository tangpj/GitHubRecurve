package tang.com.github.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import tang.com.github.BuildConfig

@Database(entities = [],version = BuildConfig.DB_VERSION_GITHUB)
abstract class GithubDb: RoomDatabase() {

    abstract fun oauthDao(): OauthDao
}