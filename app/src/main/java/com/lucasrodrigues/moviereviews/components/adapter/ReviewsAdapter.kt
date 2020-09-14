package com.lucasrodrigues.moviereviews.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.lucasrodrigues.moviereviews.components.adapter.viewholder.DataBindingViewHolder
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.utils.ReviewComparator

class ReviewsAdapter(
    private val navigationService: NavigationService,
    private val layoutId: Int
) : PagingDataAdapter<Review, DataBindingViewHolder>(ReviewComparator) {
    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item) {
            if (item != null)
                navigationService.goToReviewDetails(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }
}