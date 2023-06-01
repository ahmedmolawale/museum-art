package com.appsfactory.museum.features.arts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appsfactory.domain.model.ArtId
import com.appsfactory.museum.databinding.ArtItemBinding

typealias ArtClickListener = (ArtId) -> Unit

class ArtsAdapter(private val onClick: ArtClickListener) :
    ListAdapter<ArtId, ArtsAdapter.ViewHolder>(
        ArtDiffCallback(),
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent,
        )
    }

    class ViewHolder private constructor(private val binding: ArtItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArtId, onClick: ArtClickListener) {
            with(binding) {
                artIdTextView.text = item.value.toString()
                root.setOnClickListener { onClick(item) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ArtItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding,
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
class ArtDiffCallback : DiffUtil.ItemCallback<ArtId>() {
    override fun areItemsTheSame(
        oldItem: ArtId,
        newItem: ArtId,
    ): Boolean {
        return oldItem.value == newItem.value
    }

    override fun areContentsTheSame(
        oldItem: ArtId,
        newItem: ArtId,
    ): Boolean {
        return oldItem == newItem
    }
}
