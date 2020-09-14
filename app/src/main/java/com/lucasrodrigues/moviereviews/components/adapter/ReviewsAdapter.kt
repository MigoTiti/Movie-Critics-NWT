package com.lucasrodrigues.moviereviews.components.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.components.adapter.viewholder.DataBindingViewHolder
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.utils.ReviewComparator


class ReviewsAdapter(
    private val navigationService: NavigationService,
    private val layoutId: Int
) : PagingDataAdapter<Review, DataBindingViewHolder>(ReviewComparator) {
    private var lastPosition = -1

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item) {
            if (item != null)
                navigationService.goToReviewDetails(item)
        }

        setAnimation(holder.itemView, position)
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

    override fun onViewDetachedFromWindow(holder: DataBindingViewHolder) {
        holder.clearAnimation()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        val animation = AnimationUtils.loadAnimation(
            viewToAnimate.context,
            if (position > lastPosition) R.anim.slide_in_left else R.anim.slide_in_right
        )

        viewToAnimate.startAnimation(animation)

        lastPosition = position
    }
}