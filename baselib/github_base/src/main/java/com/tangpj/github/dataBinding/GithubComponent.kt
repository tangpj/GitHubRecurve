package com.tangpj.github.dataBinding

import androidx.databinding.DataBindingComponent
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import javax.inject.Inject


class GithubComponent @Inject constructor(): DataBindingComponent{

    override fun getMarkwonAdapters() = MarkwonAdapters()
}