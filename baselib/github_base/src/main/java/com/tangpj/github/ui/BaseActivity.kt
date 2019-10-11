package com.tangpj.github.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.recurve.core.resource.Status
import com.recurve.dagger2.RecurveDaggerActivity
import com.tangpj.github.GithubApp
import com.tangpj.github.R
import com.tangpj.github.databinding.ActivityBaseBinding
import com.tangpj.github.ui.loadState.Loading
import com.tangpj.github.ui.loadState.MultipleLoading
import com.tangpj.github.utils.THEME_ID
import com.tangpj.github.utils.installAppThemeSp

abstract class BaseActivity : RecurveDaggerActivity(){

    private var mProgressBarEnable: Boolean = false
    private lateinit var baseBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(GithubApp.getInstance().getAppThemeId())
        super.onCreate(savedInstanceState)
        installAppThemeSp(GithubApp.getInstance()).edit().putInt(THEME_ID, R.style.AppTheme_Night).apply()
    }

    fun <Binding : ViewDataBinding> initContentBinding(layoutId: Int, progressBarEnable: Boolean = false): Binding {
        mProgressBarEnable = progressBarEnable
        if (progressBarEnable){
             baseBinding = initContentBinding(R.layout.activity_base)
            val contentBinding: Binding = DataBindingUtil.inflate(layoutInflater, layoutId, baseBinding.llBase, false)
            baseBinding.llBase.addView(contentBinding.root,0)
            return contentBinding
        }
        return initContentBinding(layoutId)
    }

    fun  multipleLoading(loadingInvoke: MultipleLoading.() -> Unit){
        if (!mProgressBarEnable){
            return
        }
        val loadState =  MultipleLoading()
        loadState.loadingInvoke()
        loadState.loadingResource.observe(this, Observer {
            baseBinding.pbBase.visibility = if(it.networkState.status == Status.LOADING){
                View.VISIBLE
            }else{
                View.GONE
            }
        })

    }

}