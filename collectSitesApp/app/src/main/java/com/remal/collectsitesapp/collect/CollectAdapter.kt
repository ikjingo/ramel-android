package com.remal.collectsitesapp.collect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.remal.collectsitesapp.data.Collect
import com.remal.collectsitesapp.databinding.CollectItemCardviewBinding

/**
 * Adapter for the task list. Has a reference to the [CollectViewModel] to send actions back to it.
 */
class CollectAdapter(private val viewModel: CollectViewModel) :
    ListAdapter<Collect, CollectAdapter.ViewHolder>(CollectDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CollectItemCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CollectViewModel, item: Collect) {

            binding.viewmodel = viewModel
            binding.collect = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CollectItemCardviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
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
class CollectDiffCallback : DiffUtil.ItemCallback<Collect>() {
    override fun areItemsTheSame(oldItem: Collect, newItem: Collect): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Collect, newItem: Collect): Boolean {
        return oldItem == newItem
    }
}
