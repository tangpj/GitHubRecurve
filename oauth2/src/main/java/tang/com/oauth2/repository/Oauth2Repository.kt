package tang.com.oauth.repository

import android.net.Uri
import tang.com.github.db.GithubDb
import tang.com.github.db.GithubTokenDao
import tang.com.oauth.BuildConfig
import tang.com.oauth.api.OAuthService
import javax.inject.Inject

class Oauth2Repository @Inject
constructor(val db: GithubDb, val dao: GithubTokenDao, val oauthService: OAuthService){

    fun getGithubToken(){

    }
}
