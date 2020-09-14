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
class SearchResultsViewModel constructor(
    navigationService: NavigationService,
    alertService: AlertService,
    val reviewRepository: ReviewRepository,
    val queryString: String
) : BaseViewModel(navigationService, alertService) {

    fun getSearchResults(): Flowable<PagingData<Review>> {
        return reviewRepository
            .searchReviewsPagedList(queryString)
            .cachedIn(viewModelScope)
    }
}