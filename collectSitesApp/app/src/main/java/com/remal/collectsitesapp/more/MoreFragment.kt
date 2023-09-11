package com.remal.collectsitesapp.collect

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.remal.collectsitesapp.R
import com.remal.collectsitesapp.databinding.MoreFragBinding

class MoreFragment : Fragment() {
    private lateinit var viewDataBinding: MoreFragBinding
    private lateinit var viewModel: MoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = MoreViewModel()
        viewDataBinding = MoreFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_clear -> {
                true
            }
            R.id.menu_refresh -> {
                true
            }
            else -> false
        }
}