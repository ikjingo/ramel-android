package com.remal.collectsitesapp.site

import android.os.Bundle
import android.view.*
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.remal.collectsitesapp.R
import com.remal.collectsitesapp.databinding.SiteFragBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SiteFragment : Fragment() {
    private lateinit var viewDataBinding: SiteFragBinding
    private lateinit var viewModel: SiteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = SiteViewModel()
        viewDataBinding = SiteFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupWebView()
    }

    private fun setupWebView() {
       val mWebView = view?.findViewById<WebView>(R.id.webview)
        mWebView?.loadUrl("https://google.com/")
    }
}