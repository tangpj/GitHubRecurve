package tang.com.oauth.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import tang.com.github.pojo.GithubToken

class Oauth2ViewModel: ViewModel() {
    private val token: MutableLiveData<GithubToken> = MutableLiveData()
}