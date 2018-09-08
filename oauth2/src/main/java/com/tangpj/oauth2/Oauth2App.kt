package com.tangpj.oauth2

import com.tangpj.github.GithubApp
import com.tangpj.oauth2.di.DaggerOauth2Component

/**
 * @ClassName: Oauth2App
 * @author create by Tang
 * @date 2018/9/7 下午2:42
 * @Description: 测试用Application，
 */
class Oauth2App : GithubApp(){

    override fun componentInject() {
        DaggerOauth2Component.builder().application(this).build().inject(this)
    }

}