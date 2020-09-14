package com.lucasrodrigues.moviereviews.framework

import com.lucasrodrigues.moviereviews.model.Review

interface NavigationService {

    fun goBack()
	fun goBackWithData(vararg params: Pair<String, Any?>)
    fun goToReviewDetails(review: Review)
    fun goToFavoriteReviews()
    fun shareReview(review: Review)
}
