package com.lucasrodrigues.moviereviews.webservice

import com.lucasrodrigues.moviereviews.model.Review
import io.reactivex.rxjava3.core.Single

interface ReviewWebservice {
    fun fetchAllReviews(offset: Int = 0): Single<Pair<Int?, List<Review>>>

    fun searchReviews(
        offset: Int = 0,
        query: String,
    ): Single<Pair<Int?, List<Review>>>
}