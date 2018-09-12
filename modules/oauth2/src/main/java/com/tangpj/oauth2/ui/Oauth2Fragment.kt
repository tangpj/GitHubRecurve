package com.tangpj.oauth2.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tangpj.oauth.databinding.FragmentOauth2Binding
import com.tangpj.recurve.di.Injectable
import com.tangpj.recurve.util.openInCustomTabOrBrowser
import javax.inject.Inject

class Oauth2Fragment : Fragment(), Injectable {

    private lateinit var oauth2ViewModel: Oauth2ViewModel
    private lateinit var binding: FragmentOauth2Binding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val authorizeListener = View.OnClickListener{  _: View -> authorize() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        oauth2ViewModel = ViewModelProviders.of(this, viewModelFactory)[Oauth2ViewModel::class.java]
        val params = Oauth2FragmentArgs.fromBundle(arguments)
        getToken(params.code)
        oauth2ViewModel.token.observeForever {
            println(it)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOauth2Binding.inflate(inflater,container, false)
        binding.authorizeListener = authorizeListener
        return binding.root
    }

    private fun authorize(){
        val uri = oauth2ViewModel.buildAuthorizeUri()
        openInCustomTabOrBrowser(context, uri)
    }

    private fun getToken(code: String?){
        code?.let {
            oauth2ViewModel.refreshCode(code)
        }

    }
}