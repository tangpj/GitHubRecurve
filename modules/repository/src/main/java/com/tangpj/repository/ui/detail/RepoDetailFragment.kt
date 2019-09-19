package com.tangpj.repository.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.navPager.setupWithNavController
import com.tangpj.recurve.dagger2.RecurveDaggerFragment
import com.tangpj.repository.R
import com.tangpj.repository.databinding.FragmentPathFilesBinding
import com.tangpj.repository.databinding.FragmentRepoDetailBinding
import com.tangpj.repository.ui.creator.PathAdapter
import com.tangpj.repository.ui.creator.PathItem
import com.tangpj.repository.ui.detail.files.FilesFragmentDirections
import com.tangpj.tabPager.TabLayoutMediator

class RepoDetailFragment : RecurveDaggerFragment() {

    private var currentNavController = MutableLiveData<NavController>()

    private var filePathAdapter: PathAdapter? = null

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
        val graphIds = args.graphIds?.toList()
        graphIds ?: return
        val observerFun= binding.pagerRepo
                .setupWithNavController(childFragmentManager, lifecycle, graphIds, activity?.intent) { position ->
                    when (position) {
                        1 -> R.layout.fragment_path_files to { fragmentBinding ->
                            initFilesPath(fragmentBinding as FragmentPathFilesBinding, args)
                        }
                        else -> {
                            null
                        }
                    }
                }

        //page first init
        observerFun.observe(this, Observer {
            it {  firstInit, navController ->
                if(firstInit){
                    navController.setGraph(navController.graph, args.toBundle())
                }
                pagerInit(navController)

                currentNavController.value = navController
            }})

        TabLayoutMediator(
                binding.tabRepo,
                binding.pagerRepo) { tab, position ->
             args.tabTitles?.toList()?.apply {
                tab.text = if (position < this.size){
                    get(position)
                }else{
                    ""
                }
            }

        }.attach()
    }

    private fun pagerInit(navController: NavController){
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.files) {
                val path =  arguments?.getString("path") ?: ""
                val subPath = if (path.startsWith(':')){
                    path.substring(1)
                }else{
                    path
                }
                val pathList = subPath.split('/')
                val pathName = if (pathList.isNotEmpty()) {
                    pathList.last()
                } else {
                    ""
                }

                val pathItem = PathItem(path = path, name = pathName)
                filePathAdapter?.pushPathItem(pathItem)

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFilesPath(binding: FragmentPathFilesBinding, args: RepoDetailFragmentArgs){
        filePathAdapter = PathAdapter()
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
        filePathAdapter?.apply {
            onItemClickListener = { _, pathItem, position ->
                currentNavController.value?.let {
                    val action = FilesFragmentDirections.actionFiles().apply {
                        repoDetailQuery = args.repoDetailQuery
                        branch = args.branch
                        path = pathItem.path
                    }
                    if (position == itemCount - 1){
                        return@let
                    }
                    if (pathItem.path.isBlank()){
                        it.setGraph(it.graph, action.arguments)
                    }else{
                        it.navigate(action)
                    }
                }
            }
        }

    }

}