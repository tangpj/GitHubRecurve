package com.tangpj.oauth2.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tangpj.oauth.databinding.FragmentOauth2Binding
import com.tangpj.recurve.util.openInCustomTabOrBrowser

class Oauth2Fragment : Fragment(){

    private lateinit var oauth2ViewModel: Oauth2ViewModel
    private lateinit var binding: FragmentOauth2Binding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOauth2Binding.inflate(inflater,container, false)
        return binding.root
    }

    fun authorize(){
        val uri = oauth2ViewModel.buildAuthorizeUri()
        openInCustomTabOrBrowser(context, uri)
    }

    fun getToken(data: Uri){
        oauth2ViewModel.refreshCode(data)
    }
}