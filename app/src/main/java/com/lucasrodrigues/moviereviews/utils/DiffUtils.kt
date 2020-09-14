package com.lucasrodrigues.moviereviews.utils

import androidx.recyclerview.widget.DiffUtil
import com.lucasrodrigues.moviereviews.model.Review

object ReviewComparator : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.movieTitle == newItem.movieTitle
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}