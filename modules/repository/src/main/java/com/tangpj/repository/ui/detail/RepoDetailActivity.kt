package com.tangpj.repository.ui.detail

import android.os.Bundle
import android.util.SparseIntArray
import androidx.core.util.containsKey
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.recurve.util.resolveColor
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDeatilBinding
import com.tangpj.repository.databinding.CollasingRepoDetailBinding
import com.tangpj.repository.ui.detail.fileContent.FileContentFragmentArgs
import com.tangpj.repository.ui.detail.fileContent.FileContentFragmentDirections
import com.tangpj.repository.ui.detail.files.FilesFragmentDirections
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.viewpager.setupWithNavController
import javax.inject.Inject

const val KEY_REPO_DETAIL_QUERY = "com.tangpj.repository.ui.detail.KEY_FILE_CONTENT_QUERY"
private const val PATH_README = "README.md"
private const val BRANCH_MASTER = "master"

@Route(path = PATH_REPO_DETAILS)
class RepoDetailActivity : BaseActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoDetailViewModel: RepoDetailViewModel

    private val isFirstPager = SparseIntArray()

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
                contentScrimColorInt = resolveColor(this@RepoDetailActivity, R.attr.colorPrimary)
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
        initViewPager(binding, repoDetailQuery, "master", "README.md")

    }

    private fun initViewPager(
            binding: ActivityRepoDeatilBinding,
            repoDetailQuery: RepoDetailQuery,
            branch: String,
            path: String){
        val graphIds = listOf(R.navigation.repo_file_content, R.navigation.repo_files)
        val navController =
                binding.vpRepoContent.setupWithNavController(this, graphIds, intent )
        navController.observe(this, Observer {
            val currentId = it.currentDestination?.id ?: return@Observer
            if (isFirstPager.containsKey(currentId)){
                return@Observer
            }
            isFirstPager.append(currentId, -1)
            when(it.currentDestination?.id){
                R.id.filesScreen -> {
                    val fileContent =
                            FileContentFragmentDirections.fileContentInit().apply {
                                this.repoDetailQuery = repoDetailQuery
                                this.branch = branch
                                this.path = path
                            }.arguments
                    it.setGraph(it.graph, fileContent)
                }
                R.id.files ->{
                    val filesDirection = FilesFragmentDirections.filesInit()
                    filesDirection.repoDetailQuery = repoDetailQuery
                }
            }
        })

        TabLayoutMediator(
                binding.tlRepoTitle,
                binding.vpRepoContent){ tab, position ->
            tab.text = when(position){
                1 -> "FILED"
                else -> "README"
            }
        }.attach()
    }


}

internal fun FileContentFragmentArgs.convertToGitObject() : GitObjectQuery? {
    return repoDetailQuery?.let {
        GitObjectQuery(
                repoDetailQuery = it,
                branch = branch,
                path = path)
    }
}

