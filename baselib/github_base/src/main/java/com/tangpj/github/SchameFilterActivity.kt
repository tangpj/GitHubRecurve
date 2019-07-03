package com.tangpj.github

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

class SchameFilterActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent?.data
        ARouter.getInstance().build(uri).navigation()
        finish()
    }
}
