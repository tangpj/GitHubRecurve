package com.tangpj.pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
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
import com.recurve.dagger2.RecurveDaggerFragment
import com.recurve.navPager.setupWithNavController
import com.recurve.tabPager.TabLayoutMediator
import com.tangpj.pager.databinding.FragmentPagerBinding
import com.tangpj.pager.databinding.FragmentPagerPathBinding

import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

class PagerFragment : RecurveDaggerFragment() {

    private var currentNavController = MutableLiveData<NavController>()

    @Inject
    lateinit var filePathAdapter: PathAdapter

    private lateinit var fragmentPagerBinding: FragmentPagerBinding

    private var pagerPathConfig: PagerPathConfig? = null

    override fun onCreateBinding(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        fragmentPagerBinding = FragmentPagerBinding.inflate(inflater, container, false)
        arguments?.let {
            initViewPager(fragmentPagerBinding, PagerFragmentArgs.fromBundle(it))
        }
        return fragmentPagerBinding
    }

    private fun initViewPager(
            binding: FragmentPagerBinding,
            args: PagerFragmentArgs) {
        binding.pager.isUserInputEnabled
        val graphIds = args.graphIds?.toList()
        pagerPathConfig = args.pathConfig
        graphIds ?: return
        val observerFun= binding.pager
                .setupWithNavController(childFragmentManager, lifecycle, graphIds, activity?.intent) { position ->
                    when (position) {
                        1 -> R.layout.fragment_pager_path to { fragmentBinding ->
                            initFilesPath(fragmentBinding as FragmentPagerPathBinding, args)
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
                    navController.setGraph(navController.graph, args.params)
                }
                pagerInit(navController)

                currentNavController.value = navController
            }})

        TabLayoutMediator(
                binding.tabPager,
                binding.pager) { tab, position ->
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
            val size = pagerPathConfig?.showPathIds?.filter { destination.id == it }?.size ?: 0
            if(size > 0){
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
                filePathAdapter.pushPathItem(pathItem)

            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFilesPath(binding: FragmentPagerPathBinding, args: PagerFragmentArgs){
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
                            fragmentPagerBinding.pager.isUserInputEnabled =
                                    isScrollingRight && (binding.rvPath.layoutManager as LinearLayoutManager)
                                            .findLastCompletelyVisibleItemPosition() == binding.rvPath.adapter?.itemCount ?: 0- 1 ||
                                            !isScrollingRight && (binding.rvPath.layoutManager as LinearLayoutManager)
                                            .findFirstCompletelyVisibleItemPosition() == 0
                        }
                        MotionEvent.ACTION_UP -> {
                            lastX = 0
                            fragmentPagerBinding.pager.isUserInputEnabled = true
                        }
                    }
                    return false
                }

                override fun onTouchEvent(@NonNull rv: RecyclerView, @NonNull e: MotionEvent) {}

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

                }
            })
        }
        filePathAdapter.onItemClickListener = { _, pathItem, position ->
            currentNavController.value?.let {
                pagerPathConfig?.apply {
                    if (position != filePathAdapter.itemCount - 1){
                        this.clickAction?.onClick(it, pathItem, position)
                    }
                }

            }
        }

    }

}

@Parcelize
class PagerPathConfig(val showPathIds: List<Int>, val clickAction: ClickAction? = null) : Parcelable

@Parcelize
open class ClickAction : Parcelable{
    open fun onClick(navController: NavController, pathItem: PathItem, position: Int){}
}