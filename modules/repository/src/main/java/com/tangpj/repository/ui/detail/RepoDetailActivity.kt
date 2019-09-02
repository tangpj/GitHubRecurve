package com.tangpj.repository.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.core.util.containsKey
import androidx.core.util.set
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tangpj.github.ui.BaseActivity
import com.tangpj.github.ui.TabLayoutMediator
import com.tangpj.recurve.util.getColorByAttr
import com.tangpj.repository.PATH_REPO_DETAILS
import com.tangpj.repository.R
import com.tangpj.repository.databinding.ActivityRepoDetailBinding
import com.tangpj.repository.databinding.CollasingRepoDetailBinding
import com.tangpj.repository.databinding.FragmentPathFilesBinding
import com.tangpj.repository.ui.creator.PathAdapter
import com.tangpj.repository.ui.creator.PathItem
import com.tangpj.repository.ui.detail.files.FilesFragmentArgs
import com.tangpj.repository.ui.detail.files.FilesFragmentDirections
import com.tangpj.repository.ui.detail.viewer.ViewerFragmentArgs
import com.tangpj.repository.ui.detail.viewer.ViewerFragmentDirections
import com.tangpj.repository.valueObject.query.GitObjectQuery
import com.tangpj.repository.valueObject.query.RepoDetailQuery
import com.tangpj.viewpager.setupWithNavController
import javax.inject.Inject

const val KEY_REPO_DETAIL_QUERY = "com.tangpj.repository.ui.detail.KEY_FILE_CONTENT_QUERY"
@Route(path = PATH_REPO_DETAILS)
class RepoDetailActivity : BaseActivity(){

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var currentRepoDetailQuery: RepoDetailQuery

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoDetailViewModel: RepoDetailViewModel
    private var currentBranch = "master"

    private val isInitPage = SparseBooleanArray()

    private val filePathAdapter = PathAdapter()

    private lateinit var activityRepoDetailBinding: ActivityRepoDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRepoDetailBinding = initContentBinding(R.layout.activity_repo_detail)
        activityRepoDetailBinding.lifecycleOwner = this
        val repoDetailQuery = intent.getParcelableExtra<RepoDetailQuery>(KEY_REPO_DETAIL_QUERY)
        repoDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepoDetailViewModel::class.java)
        initView(repoDetailQuery, activityRepoDetailBinding)
        currentRepoDetailQuery = repoDetailQuery
        repoDetailViewModel.loadRepoDetail(repoDetailQuery.login, repoDetailQuery.name)
    }


    private fun initView(repoDetailQuery: RepoDetailQuery, binding: ActivityRepoDetailBinding) {
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
        initViewPager(binding, repoDetailQuery, currentBranch)

    }

    private fun initViewPager(
            binding: ActivityRepoDetailBinding,
            repoDetailQuery: RepoDetailQuery,
            branch: String){
        isInitPage.append(R.id.files_screen, false)
        isInitPage.append(R.id.files, false)
        binding.vpRepoContent.isUserInputEnabled
        val graphIds = listOf(R.navigation.repo_file_content, R.navigation.repo_files)
        val navController = binding.vpRepoContent
                .setupWithNavController(this, graphIds,intent){ position ->
                    when (position){
                        1 -> R.layout.fragment_path_files to { fragmentBinding ->
                            initFilesPath(fragmentBinding as FragmentPathFilesBinding)
                        }
                        else -> {
                            null
                        }
                    }
                }
        //page first init
        navController.observe(this, Observer {
            it ?: return@Observer
            val currentId =  it.currentDestination?.id
            it.addOnDestinationChangedListener { _, destination, arguments ->
                if (destination.id == R.id.files) {
                    val filesArgs = FilesFragmentArgs.fromBundle(arguments ?: Bundle())
                    val pathList = filesArgs.path?.split('/') ?: emptyList()
                    val pathName =  if (pathList.isNotEmpty()){
                        pathList.last()
                    }else{
                        ""
                    }
                    val pathItem = PathItem(path = filesArgs.path ?: "", name = pathName)
                    filePathAdapter.pushPathItem(pathItem)
                }

            }
            setupActionBarWithNavController(it)
            if (currentId == null || !isInitPage.containsKey(currentId) || isInitPage.get(currentId)){
                return@Observer
            }
            val args: Bundle = when(currentId){
                R.id.files_screen -> {
                    ViewerFragmentDirections.fileContentInit().apply {
                        path = "README.md"
                        this.repoDetailQuery = repoDetailQuery
                        this.branch = branch
                    }.arguments
                }
                R.id.files ->{
                    FilesFragmentDirections.actionFiles().apply {
                        this.repoDetailQuery = repoDetailQuery
                        this.branch = branch
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
        }.attach() }

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
                            activityRepoDetailBinding.vpRepoContent.isUserInputEnabled =
                                    isScrollingRight && (binding.rvPath.layoutManager as LinearLayoutManager)
                                            .findLastCompletelyVisibleItemPosition() == binding.rvPath.adapter?.itemCount ?: 0- 1 ||
                                    !isScrollingRight && (binding.rvPath.layoutManager as LinearLayoutManager)
                                            .findFirstCompletelyVisibleItemPosition() == 0
                        }
                        MotionEvent.ACTION_UP -> {
                            lastX = 0
                            activityRepoDetailBinding.vpRepoContent.isUserInputEnabled = true
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

    override fun onNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
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

