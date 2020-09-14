package com.lucasrodrigues.moviereviews.dependencies

import android.app.Activity
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.viewmodel.FavoriteReviewsViewModel
import com.lucasrodrigues.moviereviews.viewmodel.MainViewModel
import com.lucasrodrigues.moviereviews.viewmodel.ReviewDetailsViewModel
import com.lucasrodrigues.moviereviews.viewmodel.SearchResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

@ExperimentalPagingApi
object ViewModelDependencies {
    val module = module(override = true) {
        viewModel { (activity: Activity) ->
            MainViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                reviewRepository = get()
            )
        }

        viewModel { (activity: Activity, queryString: String) ->
            SearchResultsViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                reviewRepository = get(),
                queryString = queryString
            )
        }

        viewModel { (activity: Activity) ->
            FavoriteReviewsViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                reviewRepository = get()
            )
        }

        viewModel { (activity: Activity, review: Review) ->
            ReviewDetailsViewModel(
                initialReview = review,
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                reviewRepository = get()
            )
        }
    }
}