package com.lucasrodrigues.moviereviews.components.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.empty
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.framework.ResourceService
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.verticalReview

class FavoriteListController(
    private val navigationService: NavigationService,
    private val resourceService: ResourceService
) : TypedEpoxyController<List<Review>>() {
    override fun buildModels(data: List<Review>?) {
        if (data?.isEmpty() == true)
            empty {
                id("empty")
                item(resourceService.getString(R.string.empty_favorite_list))
            }
        else
            data?.forEach { review ->
                verticalReview {
                    id(review.movieTitle)
                    item(review)
                    onClick { _ ->
                        navigationService.goToReviewDetails(review)
                    }
                }
            }
    }
}