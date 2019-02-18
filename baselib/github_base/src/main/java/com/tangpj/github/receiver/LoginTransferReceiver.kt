package com.tangpj.github.receiver

import android.content.Context
import android.content.Intent
import com.tangpj.github.db.GithubTokenDao
import com.tangpj.github.pojo.GithubToken
import dagger.android.DaggerBroadcastReceiver
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject



/**
 * @ClassName: LoginTransferReceiver
 * @author create by Tang
 * @date 2019/2/17 3:11 PM
 * @Description: TODO
 */
class LoginTransferReceiver : DaggerBroadcastReceiver(){

    companion object {
        @JvmStatic
        private val KEY_TOKEN = "access_token"
    }


    @Inject
    lateinit var tokenDao: GithubTokenDao

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val token = intent?.getParcelableExtra<GithubToken>(KEY_TOKEN)
        Timber.d("login succeed, token = $token")
        token?.let {
            Single.just(it)
                    .observeOn(Schedulers.io())
                    .subscribe{ result ->
                        tokenDao.insert(result)
                    }

        }


    }

}