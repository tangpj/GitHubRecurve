package com.tangpj.repository.ui.detail

import android.os.Bundle
import android.util.SparseArray
import android.util.SparseIntArray
import androidx.core.util.containsKey
import androidx.core.util.set
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.recurve.util.getColorByAttr
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDeatilBinding
import com.tangpj.repository.databinding.CollasingRepoDetailBinding
import com.tangpj.repository.ui.detail.files.FilesFragmentDirections
import com.tangpj.repository.ui.detail.viewer.ViewerFragmentArgs
import com.tangpj.repository.ui.detail.viewer.ViewerFragmentDirections
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.viewpager.setupWithNavController
import javax.inject.Inject

const val KEY_REPO_DETAIL_QUERY = "com.tangpj.repository.ui.detail.KEY_FILE_CONTENT_QUERY"
private const val PATH_README = "README.md"
private const val BRANCH_MASTER = "master"

@Route(path = PATH_REPO_DETAILS)
class RepoDetailActivity : BaseActivity(){

    private var currentNavController: LiveData<NavController>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoDetailViewModel: RepoDetailViewModel

    private val isInitPage = SparseArray<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initContentBinding<ActivityRepoDeatilBinding>(R.layout.activity_repo_deatil)
        binding.lifecycleOwner = this
        val repoDetailQuery = intent.getParcelableExtra<RepoDetailQuery>(KEY_REPO_DETAIL_QUERY)
        repoDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepoDetailViewModel::class.java)
        initView(repoDetailQuery, binding)
        repoDetailViewModel.loadRepoDetail(repoDetailQuery.login, repoDetailQuery.name)
    }


    private fun initView(repoDetailQuery: RepoDetailQuery, binding: ActivityRepoDeatilBinding) {
        appbar {
            scrollEnable = true
            scrollFlags = "scroll|exitUntilCollapsed"
            collapsingToolbar {
                contentScrimColorInt = getColorByAttr(this@RepoDetailActivity, R.attr.colorPrimary)
                expandedTitleGravity = "top|start"
                toolBar {
                    title = "${repoDetailQuery.login}/${repoDetailQuery.name}"
                }
                collapsingView { inflater, collapsingToolbarLayout ->
                    val content = CollasingRepoDetailBinding.inflate(inflater, collapsingToolbarLayout, false)
                    content.lifecycleOwner = this@RepoDetailActivity
                    content.repoDetail = repoDetailViewModel.repoDetail
                    content.root
                }
            }
        }
        initViewPager(binding, repoDetailQuery, "master")

    }

    private fun initViewPager(
            binding: ActivityRepoDeatilBinding,
            repoDetailQuery: RepoDetailQuery,
            branch: String){
        isInitPage.append(R.id.files_screen, false)
        isInitPage.append(R.id.files, false)
        val graphIds = listOf(R.navigation.repo_file_content, R.navigation.repo_files)
        val navController =
                binding.vpRepoContent.setupWithNavController(this, graphIds, intent )
        navController.observe(this, Observer {
            it ?: return@Observer
            val currentId =  it.currentDestination?.id
            setupActionBarWithNavController(it)
            if (currentId == null || !isInitPage.containsKey(currentId) || isInitPage.get(currentId)){
                return@Observer
            }
            val args: Bundle = when(currentId){
                R.id.files_screen -> {
                    ViewerFragmentDirections.fileContentInit().apply {
                        this.repoDetailQuery = repoDetailQuery
                        this.branch = branch
                        this.path = "README.md"
                    }.arguments
                }
                R.id.files ->{
                    FilesFragmentDirections.actionFiles().apply {
                        this.repoDetailQuery = repoDetailQuery
                        this.branch = branch
                        this.path = ""
                    }.arguments
                }
                else -> Bundle()
            }
            isInitPage[currentId] = true
            it.setGraph(it.graph, args)
        })

        currentNavController = navController

        TabLayoutMediator(
                binding.tlRepoTitle,
                binding.vpRepoContent){ tab, position ->
            tab.text = when(position){
                1 -> "FILES"
                else -> "README"
            }
        }.attach()
    }

    override fun onNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}

internal fun ViewerFragmentArgs.convertToGitObject() : GitObjectQuery? {
    return repoDetailQuery?.let {
        GitObjectQuery(
                repoDetailQuery = it,
                branch = branch,
                path = path)
    }
}

