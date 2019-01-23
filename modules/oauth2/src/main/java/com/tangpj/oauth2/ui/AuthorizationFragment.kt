package com.tangpj.oauth2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tangpj.github.GithubApp
import com.tangpj.oauth2.databinding.FragmentOauth2Binding
import com.tangpj.recurve.util.openInCustomTabOrBrowser
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class AuthorizationFragment : DaggerFragment() {

    private lateinit var authorizationViewModel: AuthorizationViewModel
    private lateinit var binding: FragmentOauth2Binding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var githubApp : GithubApp

    private val authorizeListener = View.OnClickListener{  _: View -> authorize() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        authorizationViewModel = ViewModelProviders.of(this, viewModelFactory)[AuthorizationViewModel::class.java]
        val params = AuthorizationFragmentArgs.fromBundle(arguments)
        getToken(params.code)
        authorizationViewModel.token.observeForever {
            Timber.d(it.toString())
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOauth2Binding.inflate(inflater,container, false)
        binding.authorizeListener = authorizeListener
        return binding.root
    }

    private fun authorize(){
        val uri = authorizationViewModel.buildAuthorizeUri()
        openInCustomTabOrBrowser(context, uri)
    }

    private fun getToken(code: String?){
        code?.let {
            authorizationViewModel.refreshCode(code)
        }

    }
}