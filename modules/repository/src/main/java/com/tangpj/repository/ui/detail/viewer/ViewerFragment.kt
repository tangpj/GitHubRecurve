package com.tangpj.repository.ui.detail.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Option
import arrow.core.extensions.option.foldable.fold
import arrow.core.toOption
import com.prettifier.pretty.PrettifyWebView.OnContentChangedListener
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.ui.detail.convertToGitObject
import com.tangpj.repository.entity.domain.file.FileContent
import com.tangpj.repository.ui.detail.BranchViewModel
import kotlinx.android.synthetic.main.fragment_file_content.*
import timber.log.Timber
import javax.inject.Inject

/**
 * viewer file,img,commit
 *
 * @className: ViewerFragment
 * @author create by Tang
 * @date  20:48
 */
class ViewerFragment : BaseFragment() {

    private val onContentListener = object : OnContentChangedListener{
        override fun onContentChanged(progress: Int) {
            //loading
        }

        override fun onScrollChanged(reachedTop: Boolean, scroll: Int) {
            val shouldExpand = mBinding?.webView?.scrollY == 0
            if (shouldExpand) {

                webView.isNestedScrollingEnabled = shouldExpand
                webView.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0f, 0f, 0))
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var mBinding: FragmentFileContentBinding? = null
    private lateinit var viewerViewModel: ViewerViewModel
    private lateinit var branchViewModel: BranchViewModel

    override fun onCreateContentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?): ViewDataBinding?{
        val binding = FragmentFileContentBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewerViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ViewerViewModel::class.java)
        branchViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BranchViewModel::class.java)
        mBinding?.fileContent = viewerViewModel.fileContent

        branchViewModel.currentBranch.observe(this, Observer { branch ->
            loadFileContent(branch)
        })
        loadFileContent(branch = branchViewModel.currentBranch.value )

        mBinding?.webView?.setOnContentChangedListener(onContentListener)

        loading<FileContent> {
            resource = viewerViewModel.fileContent
        }
    }

    private fun loadFileContent(branch: String?){
        arguments.toOption()
                .map { ViewerFragmentArgs.fromBundle(it) }
                .flatMap { Option.fromNullable(it.convertToGitObject(branch ?: "master")) }
                .fold({
                    Timber.d("gitObjectQuery is null")
                }, {
                    viewerViewModel.loadFileContentByQuery(it)
                })
    }

}

