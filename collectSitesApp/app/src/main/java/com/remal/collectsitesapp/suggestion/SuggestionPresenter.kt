package com.remal.collectsitesapp.suggestion

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.autocomplete.RecyclerViewPresenter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.remal.collectsitesapp.collect.CollectViewModel
import com.remal.collectsitesapp.data.Collect
import com.remal.collectsitesapp.data.Suggestion
import com.remal.collectsitesapp.databinding.SuggestionItemBinding

/**
 * Adapter for the task list. Has a reference to the [CollectViewModel] to send actions back to it.
 */
class SuggestionAdapter(private val viewModel: SuggestionViewModel) :
    ListAdapter<Suggestion, SuggestionAdapter.ViewHolder>(SuggestionCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SuggestionViewModel, item: Suggestion) {
            binding.viewmodel = viewModel
            binding.suggestion = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SuggestionItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class SuggestionCallback : DiffUtil.ItemCallback<Suggestion>() {
    override fun areItemsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
        return oldItem == newItem
    }
}

class SuggestionPresenter(context: Context?, val activity: AppCompatActivity, private val viewModel: SuggestionViewModel) :
    RecyclerViewPresenter<Suggestion?>(context) {
    protected var adapter: SuggestionAdapter? = null
    override fun getPopupDimensions(): PopupDimensions {
        val dims = PopupDimensions()
        dims.width = ViewGroup.LayoutParams.MATCH_PARENT
        dims.height = ViewGroup.LayoutParams.WRAP_CONTENT
        return dims
    }

    override fun instantiateAdapter(): RecyclerView.Adapter<*> {
        adapter = SuggestionAdapter(viewModel)
        return adapter as SuggestionAdapter
    }

    override fun onViewShown() {
        super.onViewShown()
        setItems(recyclerView!!, viewModel.suggestionItems.value)
    }

    override fun onQuery(query: CharSequence?) {
        viewModel.updateSuggestionSuggestion(adapter!!, query.toString().toLowerCase())
    }
}