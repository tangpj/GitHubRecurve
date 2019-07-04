package com.tangpj.github

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

val PATH_SCHAME_HOST = "recurve://tangpj.com"

class SchameFilterActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent?.data
        ARouter.getInstance().build(uri).navigation()
        finish()
    }
}
