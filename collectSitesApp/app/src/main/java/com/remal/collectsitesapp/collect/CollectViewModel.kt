package com.remal.collectsitesapp.collect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.remal.collectsitesapp.Event
import com.remal.collectsitesapp.ServiceLocator
import com.remal.collectsitesapp.data.Collect

/**
 * ViewModel for the task list screen.
 */
class CollectViewModel: ViewModel() {
    private val _items: MutableLiveData<ArrayList<Collect>> = filterTasks()
    val items: LiveData<ArrayList<Collect>> = _items

    fun refresh() {
        _items.value?.add(0, Collect(url = "https://www.youtube.com/"))
        _items.value?.add(0,Collect(url = "https://www.youtube.com/"))
        _items.value?.add(0,Collect(url = "https://www.youtube.com/"))
        _items.value?.add(0,Collect(url = "https://www.youtube.com/"))
    }

    private fun filterTasks(): MutableLiveData<ArrayList<Collect>> {
        val result = MutableLiveData<ArrayList<Collect>>()
        result.value = filterItems()
        return result
    }

    private fun filterItems(): ArrayList<Collect> {
        val tasksToShow = ArrayList<Collect>()
        tasksToShow.add(Collect(url = "https://www.naver.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.youtube.com/"))
        tasksToShow.add(Collect(url = "https://comic.naver.com/index.nhn"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        tasksToShow.add(Collect(url = "https://www.google.com/"))
        return tasksToShow
    }

    fun openSite(url: String) {
        ServiceLocator.openSiteEvent.value = Event(url)
    }
}