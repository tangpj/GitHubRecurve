package com.tangpj.repository.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.containsKey
import androidx.core.util.set
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.recurve.dagger2.RecurveDaggerFragment
import com.tangpj.repository.R
import com.tangpj.repository.databinding.FragmentPathFilesBinding
import com.tangpj.repository.databinding.FragmentRepoDetailBinding
import com.tangpj.repository.ui.creator.PathAdapter
import com.tangpj.repository.ui.creator.PathItem
import com.tangpj.repository.ui.detail.files.FilesFragmentArgs
import com.tangpj.repository.ui.detail.files.FilesFragmentDirections
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.viewpager.setupWithNavController

class RepoDetailFragment : RecurveDaggerFragment() {

    private lateinit var currentRepoDetailQuery: RepoDetailQuery
    private var currentBranch = "master"

    private var currentNavController: LiveData<NavController>? = null

    private val filePathAdapter = PathAdapter()

    private lateinit var fragmentRepoDetailBinding: FragmentRepoDetailBinding


    override fun onCreateBinding(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        fragmentRepoDetailBinding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        arguments?.let {
            initViewPager(fragmentRepoDetailBinding, RepoDetailFragmentArgs.fromBundle(it))
        }
        return fragmentRepoDetailBinding
    }

    private fun initViewPager(
            binding: FragmentRepoDetailBinding,
            args: RepoDetailFragmentArgs) {
        binding.pagerRepo.isUserInputEnabled
        val graphIds = listOf(R.navigation.viewer, R.navigation.repo_files)
        val observerFun= binding.pagerRepo
                .setupWithNavController(childFragmentManager, lifecycle, graphIds, activity?.intent) { position ->
                    when (position) {
                        1 -> R.layout.fragment_path_files to { fragmentBinding ->
                            initFilesPath(fragmentBinding as FragmentPathFilesBinding)
                        }
                        else -> {
                            null
                        }
                    }
                }

        //page first init
        observerFun.observe(this, Observer { it{ firstInit, navController  ->
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                if (destination.id == R.id.files && firstInit) {
                    val filesArgs = FilesFragmentArgs.fromBundle(arguments ?: Bundle())
                    val pathList = filesArgs.path?.split('/') ?: emptyList()
                    val pathName = if (pathList.isNotEmpty()) {
                        pathList.last()
                    } else {
                        ""
                    }
                    val pathItem = PathItem(path = filesArgs.path ?: "", name = pathName)
                    filePathAdapter.pushPathItem(pathItem)
                }

            }
            (activity as? AppCompatActivity)?.apply {
                setupActionBarWithNavController( this, navController)
            }
            navController.setGraph(navController.graph, args.toBundle())
            currentNavController = MutableLiveData(navController)
        }})

        TabLayoutMediator(
                binding.tabRepo,
                binding.pagerRepo) { tab, position ->
            tab.text = when (position) {
                1 -> "FILES"
                else -> "README"
            }
        }.attach()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFilesPath(binding: FragmentPathFilesBinding){
        binding.rvPath.adapter  = filePathAdapter
        binding.rvPath.itemAnimator?.changeDuration = 0
        binding.rvPath.run {
            addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                var lastX = 0
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_DOWN -> lastX = e.x.toInt()
                        MotionEvent.ACTION_MOVE -> {
                            val isScrollingRight = e.x < lastX
                            fragmentRepoDetailBinding.pagerRepo.isUserInputEnabled =
                                    isScrollingRight && (binding.rvPath.layoutManager as LinearLayoutManager)
                                            .findLastCompletelyVisibleItemPosition() == binding.rvPath.adapter?.itemCount ?: 0- 1 ||
                                            !isScrollingRight && (binding.rvPath.layoutManager as LinearLayoutManager)
                                            .findFirstCompletelyVisibleItemPosition() == 0
                        }
                        MotionEvent.ACTION_UP -> {
                            lastX = 0
                            fragmentRepoDetailBinding.pagerRepo.isUserInputEnabled = true
                        }
                    }
                    return false
                }

                override fun onTouchEvent(@NonNull rv: RecyclerView, @NonNull e: MotionEvent) {}

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

                }
            })
        }
        filePathAdapter.onClickListener = { _, pathItem, position ->
            currentNavController?.value?.let {
                val action = FilesFragmentDirections.actionFiles().apply {
                    repoDetailQuery = currentRepoDetailQuery
                    branch = currentBranch
                    path = pathItem.path
                }
                if (position == filePathAdapter.itemCount - 1){
                    return@let
                }
                if (pathItem.path.isBlank()){
                    it.setGraph(it.graph, action.arguments)
                }else{
                    it.navigate(action)
                }
            }
        }

        filePathAdapter.pushPathItem(PathItem("", ""))
    }

}