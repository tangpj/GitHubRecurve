package tang.com.oauth2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import tang.com.github.api.createOauthService
import tang.com.github.pojo.GithubToken
import tang.com.oauth.BuildConfig
import tang.com.oauth2.request.RequestToken
import tang.com.recurve.util.openInCustomTabOrBrowser


class GithubOauth2 private constructor(){

    companion object {
        const val OAUTH_URL = "https://github.com/login/oauth/authorize"
        const val PARAM_CLIENT_ID = "client_id"
        const val PARAM_CODE = "code"
        const val PARAM_SCOPE = "scope"
        const val PARAM_CALLBACK_URI = "redirect_uri"
        val CALLBACK_URI = Uri.parse("recurve://oauth")

        @JvmStatic
        fun launchOauthLogin(activity: Activity) {
            val uri = Uri.parse(OAUTH_URL)
                    .buildUpon()
                    .appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
                    .appendQueryParameter(PARAM_SCOPE, BuildConfig.SCOPES)
                    .appendQueryParameter(PARAM_CALLBACK_URI, BuildConfig.REDIRECTt_URI)
                    .build()
            openInCustomTabOrBrowser(activity,uri)
        }

        @JvmStatic
        @JvmOverloads
        fun getGithubToken(intent: Intent?, callback: ((token: String) -> Unit)? = null){
            val data = intent?.data
            if(data != null &&
                    data.scheme == CALLBACK_URI.scheme &&
                    data.host == CALLBACK_URI.host){
                val service = createOauthService()
                val requestToken = RequestToken(clientId = BuildConfig.CLIENT_ID,
                        client_secret = BuildConfig.CLIENT_SECRET,
                        code = data.getQueryParameter(PARAM_CODE) ?: "")
                service.getToken(requestToken).map {
                    GithubToken(accessToken = it.headers().get("access_token") ?: "",
                            tokenType = it.headers().get("token_type"),
                            scope = it.headers().get("scope"))
                }.subscribe { t1, t2 ->
                    println()
                }

            }
        }

    }



}