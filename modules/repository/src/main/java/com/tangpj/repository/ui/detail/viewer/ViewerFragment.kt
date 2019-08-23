package com.tangpj.repository.ui.detail.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.prettifier.pretty.PrettifyWebView
import com.tangpj.github.ui.BaseFragment
import com.tangpj.repository.databinding.FragmentFileContentBinding
import com.tangpj.repository.ui.detail.convertToGitObject
import com.tangpj.repository.entry.vo.FileContent
import javax.inject.Inject

class ViewerFragment : BaseFragment(), PrettifyWebView.OnContentChangedListener{

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

        gitObjectQuery?.let {
            viewerViewModel.loadFileContentByQuery(it)
        }

        loading<FileContent> {
            resource = viewerViewModel.fileContent
        }
    }

    override fun onContentChanged(progress: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScrollChanged(reachedTop: Boolean, scroll: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

