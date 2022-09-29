package com.android4you.nasa.presentation.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android4you.nasa.R
import com.android4you.nasa.databinding.ItemGridImageBinding
import com.android4you.nasa.domain.model.ImageModelItem

class ImageGridViewHolder(private val binding: ItemGridImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImageModelItem, index: Int) {
        binding.apply {
            model = item
            executePendingBindings()
        }
        val bundle = Bundle()
        bundle.putInt("INDEX", index)
        binding.imageView.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_imageGridFragment_to_imageDetailFragment,
                bundle
            )
        )
    }

    companion object {
        fun from(parent: ViewGroup): ImageGridViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemGridImageBinding.inflate(layoutInflater, parent, false)
            return ImageGridViewHolder(binding)
        }
    }
}
