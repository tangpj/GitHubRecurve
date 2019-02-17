package com.tangpj.github.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tangpj.github.db.GithubDb
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import timber.log.Timber
import javax.inject.Inject

/**
 * @ClassName: LoginTransferReceiver
 * @author create by Tang
 * @date 2019/2/17 3:11 PM
 * @Description: TODO
 */
class LoginTransferReceiver : BroadcastReceiver(){

    companion object {
        @JvmStatic
        private val KEY_TOKEN = "access_token"
    }

    @Inject lateinit var db: GithubDb

    @Inject
    lateinit var tokenDao: GithubTokenDao

    override fun onReceive(context: Context?, intent: Intent?) {
        val token = intent?.getParcelableExtra<GithubToken>(KEY_TOKEN)
        Timber.d("login succeed, token = $token")
        token?.let {
            tokenDao.insert(it)
        }
    }

}