package com.tangpj.repository.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDeatilBinding
import com.tangpj.repository.ui.detail.fileContent.FileContentFragment

class RepoDetailActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initContentBinding<ActivityRepoDeatilBinding>(R.layout.activity_repo_deatil)
        TabLayoutMediator(binding.tlRepoTitle, binding.vpRepoContent){ tab, position ->
            TODO("set tab text")
        }.attach()
    }

    private fun createViewPagerAdapter() =
            object : FragmentStateAdapter(this){
                override fun createFragment(position: Int): Fragment {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun getItemCount(): Int = 1


            }
}