package com.remal.collectsitesapp.suggestion

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.remal.collectsitesapp.ServiceLocator
import com.remal.collectsitesapp.data.Suggestion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * ViewModel for the task list screen.
 */
class SuggestionViewModel : ViewModel() {
    val handler = Handler()
    private var _runnable: Runnable? = null

    fun DelayedAction(runnable: Runnable, delay: Long) {
        if (_runnable != null) {
            handler.removeCallbacks(_runnable!!)
        }
        _runnable = runnable
        handler.postDelayed(runnable, delay)
    }

    var _suggestionItems: LiveData<ArrayList<Suggestion>> = filterTasks()
    var suggestionItems: LiveData<ArrayList<Suggestion>> = _suggestionItems
    var testItems: LiveData<ArrayList<Suggestion>> = filterTasks()
    fun updateSuggestionSuggestion(adapter: SuggestionAdapter, query: String) {
        DelayedAction(Runnable {
            ServiceLocator.api.getSuggestion(q = query).enqueue(object : Callback<JsonArray> {
                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    suggestionItems.value?.clear()
                    (response.body()?.find { it.isJsonArray } as? JsonArray)?.forEach { suggestion ->
                        suggestionItems.value?.add(Suggestion(url = suggestion.asString))
                    }

                    testItems.value?.let {
                        it.forEach { suggestion ->
                            if (suggestion.url.toLowerCase().contains(query)) {
                                suggestionItems.value?.add(suggestion)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            })
        }, 500)
    }

    private fun filterTasks(): MutableLiveData<ArrayList<Suggestion>> {
        val result = MutableLiveData<ArrayList<Suggestion>>()
        result.value = filterItems()
        return result
    }

    private fun filterItems(): ArrayList<Suggestion> {
        val tasksToShow = ArrayList<Suggestion>()
        tasksToShow.add(Suggestion(url = "https://www.naver.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.youtube.com/"))
        tasksToShow.add(Suggestion(url = "https://comic.naver.com/index.nhn"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))
        tasksToShow.add(Suggestion(url = "https://www.google.com/"))



        return tasksToShow
    }

    class someTask() : AsyncTask<Call<JsonArray>, Void, ArrayList<Suggestion>>() {
        override fun doInBackground(vararg params: Call<JsonArray>?): ArrayList<Suggestion> {
            val result = ArrayList<Suggestion>()
            val response = params[0]?.execute()
            (response?.body()?.find { it.isJsonArray } as JsonArray).forEach { suggestion ->
                result.add(Suggestion(url = suggestion.asString))
            }
            return result
        }

        override fun onPostExecute(result: ArrayList<Suggestion>?) {
            super.onPostExecute(result)
        }
    }
}