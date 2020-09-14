package com.lucasrodrigues.moviereviews.viewmodel

import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.framework.AlertService
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.repository.ReviewRepository

@ExperimentalPagingApi
class FavoriteReviewsViewModel constructor(
    navigationService: NavigationService,
    alertService: AlertService,
    reviewRepository: ReviewRepository
) : BaseViewModel(navigationService, alertService) {

    val favoriteReviews = reviewRepository.listenToFavoriteReviews()
}