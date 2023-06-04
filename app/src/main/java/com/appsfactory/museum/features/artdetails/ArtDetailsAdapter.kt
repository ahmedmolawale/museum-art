package com.appsfactory.museum.features.artdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.appsfactory.museum.R
import com.appsfactory.museum.databinding.AdditionalImageHeaderBinding
import com.appsfactory.museum.databinding.AdditionalImageItemBinding
import com.appsfactory.museum.databinding.ArtDetailsOverviewItemBinding

class ArtDetailsAdapter :
    ListAdapter<ArtDetailsItem, RecyclerView.ViewHolder>(
        ArtDetailsDiffCallback(),
    ) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        val item = getItem(position)
        when {
            item is ArtDetailsItem.ArticleOverview && holder is OverviewViewHolder -> {
                holder.bind(item.artDetailsOverview)
            }
            item is ArtDetailsItem.AdditionalImage && holder is AdditionalImageViewHolder -> {
                holder.bind(item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (AdapterType.fromInt(viewType)) {
            AdapterType.TYPE_OVERVIEW -> OverviewViewHolder.from(parent)
            AdapterType.TYPE_HEADER -> HeaderViewHolder.from(parent)
            AdapterType.TYPE_ADDITIONAL_IMAGE -> AdditionalImageViewHolder.from(parent)
            else -> throw IllegalArgumentException("Not a valid state") //can properly handle later
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == RecyclerView.NO_POSITION) return AdapterType.TYPE_NONE.type
        return when (getItem(position)) {
            is ArtDetailsItem.Header -> AdapterType.TYPE_HEADER.type
            is ArtDetailsItem.ArticleOverview -> AdapterType.TYPE_OVERVIEW.type
            is ArtDetailsItem.AdditionalImage -> AdapterType.TYPE_ADDITIONAL_IMAGE.type
        }
    }

    class HeaderViewHolder private constructor(binding: AdditionalImageHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdditionalImageHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(
                    binding,
                )
            }
        }
    }

    class OverviewViewHolder private constructor(private val binding: ArtDetailsOverviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArtDetailsOverview) {
            with(binding) {
                artHeadlineTextview.text = item.title
                artBodyTextview.text = item.credit
            }
        }

        companion object {
            fun from(parent: ViewGroup): OverviewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ArtDetailsOverviewItemBinding.inflate(layoutInflater, parent, false)
                return OverviewViewHolder(
                    binding,
                )
            }
        }
    }

    class AdditionalImageViewHolder private constructor(private val binding: AdditionalImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.artImage.load(item) {
                placeholder(R.drawable.art_image_default_bg)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AdditionalImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdditionalImageItemBinding.inflate(layoutInflater, parent, false)
                return AdditionalImageViewHolder(
                    binding,
                )
            }
        }
    }

    enum class AdapterType(var type: Int) {
        TYPE_NONE(-1),
        TYPE_OVERVIEW(0),
        TYPE_HEADER(1),
        TYPE_ADDITIONAL_IMAGE(2),
        ;

        companion object {
            fun fromInt(value: Int): AdapterType =
                values().find { adapterType -> adapterType.type == value } ?: TYPE_NONE
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class ArtDetailsDiffCallback : DiffUtil.ItemCallback<ArtDetailsItem>() {
    override fun areItemsTheSame(
        oldItem: ArtDetailsItem,
        newItem: ArtDetailsItem,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ArtDetailsItem,
        newItem: ArtDetailsItem,
    ): Boolean {
        return oldItem == newItem
    }
}
