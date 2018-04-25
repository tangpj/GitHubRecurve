package tang.com.mvvmrecurve.api

import android.app.Activity
import android.content.Intent
import android.net.Uri
import tang.com.mvvmrecurve.BuildConfig
import tang.com.mvvmrecurve.R
import tang.com.recurve.util.IntentUtils
import tang.com.recurve.util.UiUtils
import javax.inject.Inject

class GithubOauth2 private constructor(){

    companion object {
        const val OAUTH_URL = "https://github.com/login/oauth/authorize"
        const val PARAM_CLIENT_ID = "client_id"
        const val PARAM_CODE = "code"
        const val PARAM_SCOPE = "scope"
        const val PARAM_CALLBACK_URI = "redirect_uri"
        const val SCOPES = "user,repo,gist"

        fun launchOauthLogin(activity: Activity) {
            val uri = Uri.parse(OAUTH_URL)
                    .buildUpon()
                    .appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
                    .appendQueryParameter(PARAM_SCOPE, SCOPES)
                    .appendQueryParameter(PARAM_CALLBACK_URI, BuildConfig.REDIRECTt_URI)
                    .build()
            IntentUtils.openInCustomTabOrBrowser(activity,uri)
        }

    }



}