package tang.com.mvvmrecurve.api

class GithubOauth2{

    companion object {
        const val OAUTH_URL = "https://github.com/login/oauth/authorize"
        private val PARAM_CLIENT_ID = "client_id"
        private val PARAM_CODE = "code"
        private val PARAM_SCOPE = "scope"
        private val PARAM_CALLBACK_URI = "redirect_uri"
    }
}