package com.remal.collectsitesapp.collect

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.remal.collectsitesapp.EventObserver
import com.remal.collectsitesapp.R
import com.remal.collectsitesapp.ServiceLocator
import com.remal.collectsitesapp.api.GoogleApi
import com.remal.collectsitesapp.api.GoogleSuggestion
import com.remal.collectsitesapp.data.Collect
import com.remal.collectsitesapp.databinding.CollectFragBinding
import com.remal.collectsitesapp.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectFragment : Fragment() {
    private lateinit var collectAdapter: CollectAdapter
    private lateinit var viewDataBinding: CollectFragBinding
    private lateinit var viewModel: CollectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = CollectViewModel()
        viewDataBinding = CollectFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.collect_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_clear -> {
                startActivity(Intent(this.activity, SearchActivity::class.java))
                true
            }
            R.id.menu_filter -> {
                viewModel.refresh()
                collectAdapter.notifyDataSetChanged()
                true
            }
            R.id.menu_refresh -> {
                true
            }
            else -> false
        }

    private fun setupListAdapter() {
        collectAdapter = CollectAdapter(viewDataBinding.viewmodel!!)
        viewDataBinding.collectList.let {
            it.adapter = collectAdapter
        }
    }

    private fun setupNavigation() {

    }
}