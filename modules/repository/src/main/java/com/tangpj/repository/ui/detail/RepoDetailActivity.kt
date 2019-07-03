package com.tangpj.repository.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDeatilBinding
import com.tangpj.repository.ui.detail.fileContent.FileContentFragment
import com.tangpj.repository.valueObject.query.FileContentQuery
import com.tangpj.repository.valueObject.query.RepoDetailQuery

private const val KEY_REPO_DETAIL_QUERY = "com.tangpj.repository.ui.detail.KEY_FILE_CONTENT_QUERY25"
private const val EXPERSSION_README = "master:README.md"

class RepoDetailActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initContentBinding<ActivityRepoDeatilBinding>(R.layout.activity_repo_deatil)
        binding.vpRepoContent.adapter = createViewPagerAdapter()
        TabLayoutMediator(binding.tlRepoTitle, binding.vpRepoContent){ tab, position ->
            TODO("set tab text")
        }.attach()
    }

    private fun createViewPagerAdapter() : FragmentStateAdapter{
        val repoQuery = intent.getParcelableExtra<RepoDetailQuery>(KEY_REPO_DETAIL_QUERY)
        return RepoDetailAdapter(this, repoQuery)
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
                        val fileContentQuery = FileContentQuery(repoDetailQuery, EXPERSSION_README)
                        FileContentFragment.create(fileContentQuery)
                    }
                }



            override fun getItemCount(): Int = 1


        }
