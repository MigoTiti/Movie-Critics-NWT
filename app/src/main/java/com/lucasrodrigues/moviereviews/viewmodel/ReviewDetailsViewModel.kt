package com.lucasrodrigues.moviereviews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.framework.AlertService
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.repository.ReviewRepository

@ExperimentalPagingApi
class ReviewDetailsViewModel constructor(
    navigationService: NavigationService,
    alertService: AlertService,
    private val reviewRepository: ReviewRepository,
    val initialReview: Review
) : BaseViewModel(navigationService, alertService) {

    val review = reviewRepository.listenToReview(initialReview.movieTitle)

    private val togglingFavorite = MutableLiveData(false)
    private val errorTogglingFavorite = MutableLiveData<Throwable>()

    fun toggleFavorite() {
        val review = review.value

        if (review != null && togglingFavorite.value == false)
            request(
                reviewRepository.toggleFavoriteReview(review),
                errorObservable = errorTogglingFavorite,
                loadingObservable = togglingFavorite,
            )
    }

    fun shareReview() {
        val review = review.value

        if (review != null)
            navigationService.shareReview(review)
    }
}