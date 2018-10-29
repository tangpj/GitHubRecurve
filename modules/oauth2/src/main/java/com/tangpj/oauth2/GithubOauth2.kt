package com.tangpj.oauth2

class GithubOauth2 private constructor(){

    companion object {
        const val OAUTH_URL = "https://github.com/login/oauth/authorize"
        const val PARAM_CLIENT_ID = "client_id"
        const val PARAM_CODE = "code"
        const val PARAM_SCOPE = "scope"
        const val PARAM_CALLBACK_URI = "redirect_uri"

    }


}