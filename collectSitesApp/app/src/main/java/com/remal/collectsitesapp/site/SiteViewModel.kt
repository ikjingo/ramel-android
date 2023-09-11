package com.remal.collectsitesapp.site

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.remal.collectsitesapp.data.Collect

/**
 * ViewModel for the task list screen.
 */
class SiteViewModel() : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _items: LiveData<List<Collect>> = MutableLiveData<List<Collect>>()
    val items: LiveData<List<Collect>> = _items

    fun refresh() {

    }
}