package com.tangpj.repository.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.tangpj.github.ui.BaseActivity
import com.tangpj.navigation.setupWithNavController
import com.tangpj.recurve.util.getColorByAttr
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDetailBinding
import com.tangpj.repository.databinding.CollasingRepoDetailBinding
import com.tangpj.repository.ui.detail.viewer.ViewerFragmentArgs
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import javax.inject.Inject

const val KEY_REPO_DETAIL_QUERY = "com.tangpj.repository.ui.detail.KEY_FILE_CONTENT_QUERY"
@Route(path = PATH_REPO_DETAILS)
class RepoDetailActivity : BaseActivity(){

    private lateinit var currentRepoDetailQuery: RepoDetailQuery

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoDetailViewModel: RepoDetailViewModel
    private var currentBranch = "master"
    private var currentNavController =  MutableLiveData<NavController>()


    private lateinit var activityRepoDetailBinding: ActivityRepoDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRepoDetailBinding = initContentBinding(R.layout.activity_repo_detail)
        activityRepoDetailBinding.lifecycleOwner = this
        val repoDetailQuery = intent.getParcelableExtra<RepoDetailQuery>(KEY_REPO_DETAIL_QUERY)
        repoDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepoDetailViewModel::class.java)
        initView(repoDetailQuery)
        if(savedInstanceState == null){
            setupBottomNavigationBar(repoDetailQuery, activityRepoDetailBinding)
        }
        currentRepoDetailQuery = repoDetailQuery
        repoDetailViewModel.loadRepoDetail(repoDetailQuery.login, repoDetailQuery.name)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val repoDetailQuery = intent.getParcelableExtra<RepoDetailQuery>(KEY_REPO_DETAIL_QUERY)
        setupBottomNavigationBar(repoDetailQuery, activityRepoDetailBinding)
    }


    private fun initView(repoDetailQuery: RepoDetailQuery) {
        appbar {
            scrollEnable = true
            scrollFlags = "scroll|exitUntilCollapsed"
            title = "${repoDetailQuery.login}/${repoDetailQuery.name}"
            showHomeAsUp = true
            collapsingToolbar {
                contentScrimColorInt = getColorByAttr(this@RepoDetailActivity, R.attr.colorPrimary)
                expandedTitleGravity = "top|start"
                collapsingView { inflater, collapsingToolbarLayout ->
                    val content = CollasingRepoDetailBinding.inflate(inflater, collapsingToolbarLayout, false)
                    content.lifecycleOwner = this@RepoDetailActivity
                    content.repoDetail = repoDetailViewModel.repoDetail
                    content.root
                }
            }
        }

    }

    private fun setupBottomNavigationBar(
            repoDetailQuery: RepoDetailQuery,
            activityRepoDetailBinding: ActivityRepoDetailBinding){
        val navGraphIds = listOf(R.navigation.repo_detail, R.navigation.viewer)

        val controller = activityRepoDetailBinding.bottomNav.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent)

        controller.observe(this, Observer { it {  isFirstInit, navController  ->
            if(isFirstInit){
                pagerInit(repoDetailQuery, navController)
            }
            currentNavController.value = navController
        }})
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //navigation pager init
    private fun pagerInit(
            repoDetailQuery: RepoDetailQuery,
            navController: NavController){
        val args = RepoDetailFragmentArgs.Builder().apply {
            this.repoDetailQuery = repoDetailQuery
            this.branch = currentBranch
            this.graphIds = intArrayOf(R.navigation.viewer, R.navigation.repo_files, R.navigation.commit)
        }.build().toBundle()
        navController.setGraph(navController.graph, args)

    }

}

internal fun ViewerFragmentArgs.convertToGitObject() : GitObjectQuery? {
    return repoDetailQuery?.let {
        GitObjectQuery(
                repoDetailQuery = it,
                branch = branch,
                path = path ?: "" )
    }
}

