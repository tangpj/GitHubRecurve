package tang.com.oauth2.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.net.Uri
import android.view.View
import tang.com.github.pojo.GithubToken
import tang.com.oauth.BuildConfig
import tang.com.oauth2.GithubOauth2
import tang.com.recurve.util.IntentUtils

class Oauth2ViewModel: ViewModel() {
    private val token: MutableLiveData<GithubToken> = MutableLiveData()

    private fun buildAuthorizeUri(): Uri
            = Uri.parse(GithubOauth2.OAUTH_URL)
                .buildUpon()
                .appendQueryParameter(GithubOauth2.PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
                .appendQueryParameter(GithubOauth2.PARAM_SCOPE, BuildConfig.SCOPES)
                .appendQueryParameter(GithubOauth2.PARAM_CALLBACK_URI, BuildConfig.REDIRECTt_URI)
                .build()


    fun authorize(view: View){
        val uri = buildAuthorizeUri()
        IntentUtils.openInCustomTabOrBrowser(view.context, uri)
    }
}