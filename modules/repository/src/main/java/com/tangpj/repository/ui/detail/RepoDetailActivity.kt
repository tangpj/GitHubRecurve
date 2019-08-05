package com.tangpj.repository.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.tangpj.github.ui.BaseActivity
import com.tangpj.recurve.util.resolveColor
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDeatilBinding
import com.tangpj.repository.databinding.CollasingRepoDetailBinding
import com.tangpj.repository.ui.detail.fileContent.FileContentFragment
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.viewpager.setupWithNavController

const val KEY_REPO_DETAIL_QUERY = "com.tangpj.repository.ui.detail.KEY_FILE_CONTENT_QUERY"
private const val PATH_README = "README.md"
private const val BRANCH_MASTER = "master"

@Route(path = PATH_REPO_DETAILS)
class RepoDetailActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initContentBinding<ActivityRepoDeatilBinding>(R.layout.activity_repo_deatil)
        val repoDetailQuery = intent.getParcelableExtra<RepoDetailQuery>(KEY_REPO_DETAIL_QUERY)
        val graphIds = listOf(R.navigation.navigation_repositories)
        binding.vpRepoContent.setupWithNavController(this, graphIds, intent )

        appbar {
            scrollEnable = true
            scrollFlags = "scroll|exitUntilCollapsed"
            collapsingToolbar {
                contentScrimColorInt = resolveColor(this@RepoDetailActivity, R.attr.colorPrimary)
                expandedTitleGravity = "top"
                toolBar {
                    title = "${repoDetailQuery.owner}/${repoDetailQuery.name}"
                }
                collapsingView { inflater, collapsingToolbarLayout ->
                    val content = CollasingRepoDetailBinding.inflate(inflater, collapsingToolbarLayout, false)
                    content.root
                }
            }
        }

    }

    private fun createViewPagerAdapter(repoDetailQuery: RepoDetailQuery) : FragmentStateAdapter{
        return RepoDetailAdapter(this, repoDetailQuery)
    }

}

private class RepoDetailAdapter(
        val activity: FragmentActivity,
        val repoDetailQuery: RepoDetailQuery) : FragmentStateAdapter(activity){
            override fun createFragment(position: Int): Fragment =
                when(position){
                    1 -> {
                        TODO()
                    }
                    else -> {
                        val fileContentQuery = GitObjectQuery(repoDetailQuery, BRANCH_MASTER, PATH_README)
                        FileContentFragment.create(fileContentQuery)
                    }
                }

            override fun getItemCount(): Int = 1

        }
