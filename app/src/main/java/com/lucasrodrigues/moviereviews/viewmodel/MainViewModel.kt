package com.lucasrodrigues.moviereviews.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.lucasrodrigues.moviereviews.framework.AlertService
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.repository.ReviewRepository
import io.reactivex.rxjava3.core.Flowable

@ExperimentalPagingApi
class MainViewModel constructor(
    navigationService: NavigationService,
    alertService: AlertService,
    val reviewRepository: ReviewRepository,
) : BaseViewModel(navigationService, alertService) {

    fun getAllReviews(): Flowable<PagingData<Review>> {
        return reviewRepository
            .allReviewsPagedList()
            .cachedIn(viewModelScope)
    }
}