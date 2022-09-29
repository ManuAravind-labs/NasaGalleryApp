package com.android4you.nasa.presentation.grid

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android4you.nasa.domain.model.ImageModelItem

class ImageGridAdapter : ListAdapter<ImageModelItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageGridViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val workout = getItem(position)
        if (holder is ImageGridViewHolder)
            holder.bind(workout, position)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ImageModelItem> =
            object : DiffUtil.ItemCallback<ImageModelItem>() {
                override fun areItemsTheSame(oldItem: ImageModelItem, newItem: ImageModelItem) =
                    oldItem.date == newItem.date

                override fun areContentsTheSame(oldItem: ImageModelItem, newItem: ImageModelItem) =
                    oldItem == newItem
            }
    }
}