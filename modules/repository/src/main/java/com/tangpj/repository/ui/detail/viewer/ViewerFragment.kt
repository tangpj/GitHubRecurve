package com.tangpj.repository.ui.detail.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.prettifier.pretty.PrettifyWebView.OnContentChangedListener
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.ui.detail.convertToGitObject
import com.tangpj.repository.entry.vo.FileContent
import kotlinx.android.synthetic.main.fragment_file_content.*
import javax.inject.Inject

/**
 * viewer file,img,commit
 *
 * @className: ViewerFragment
 * @author create by Tang
 * @date  20:48
 */
class ViewerFragment : BaseFragment() {

    val onContentListener = object : OnContentChangedListener{
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

    override fun onCreateContentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?): ViewDataBinding?{
        val binding = FragmentFileContentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mBinding = binding
        return mBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewerViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ViewerViewModel::class.java)
        mBinding?.fileContent = viewerViewModel.fileContent
        val gitObjectQuery = arguments?.let{
            val viewerQuery= ViewerFragmentArgs.fromBundle(it)
            viewerQuery.convertToGitObject()
        }

        mBinding?.webView?.setOnContentChangedListener(onContentListener)
        gitObjectQuery?.let {
            viewerViewModel.loadFileContentByQuery(it)
        }

        loading<FileContent> {
            resource = viewerViewModel.fileContent
        }
    }

}

