package com.tangpj.repository.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.recurve.util.resolveColor
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDeatilBinding
import com.tangpj.repository.databinding.CollasingRepoDetailBinding
import com.tangpj.repository.ui.detail.fileContent.FileContentFragment
import com.tangpj.repository.ui.detail.fileContent.FileContentFragmentDirections
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


    private fun initView(repoDetailQuery: RepoDetailQuery, binding: ActivityRepoDeatilBinding){
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

        val graphIds = listOf(R.navigation.navigation_repo_detail)
        val navController =
                binding.vpRepoContent.setupWithNavController(this, graphIds, intent )
        navController.observe(this, Observer {
            when(it.currentDestination?.id){
                R.id.filesScreen -> {
                    val fileContent =
                            FileContentFragmentDirections.fileContentInit()
                    fileContent.repoDetailQuery = repoDetailQuery
                    it.navigate(fileContent)
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


