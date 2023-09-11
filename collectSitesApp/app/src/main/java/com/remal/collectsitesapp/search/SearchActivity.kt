package com.remal.collectsitesapp.search

import com.remal.collectsitesapp.R

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.remal.collectsitesapp.databinding.SearchActBinding

/**
 * Main activity for the todoapp. Holds the Navigation Host Fragment and the Drawer, Toolbar, etc.
 */
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: SearchActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.search_act)
    }
}