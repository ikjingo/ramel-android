package com.remal.collectsitesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.remal.collectsitesapp.api.GoogleApi
import com.remal.collectsitesapp.data.source.CollectRepository

object ServiceLocator {
    @Volatile
    var collectRepository: CollectRepository? = null

    val openSiteEvent: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()

    val api by lazy {
        GoogleApi.create()
    }
}