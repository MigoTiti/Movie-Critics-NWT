package com.lucasrodrigues.moviereviews.webservice.impl

import com.lucasrodrigues.moviereviews.dataaccess.network.ReviewApi
import com.lucasrodrigues.moviereviews.extensions.toReview
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.webservice.ReviewWebservice
import io.reactivex.rxjava3.core.Single

class ReviewWebserviceImpl(
    private val reviewApi: ReviewApi
) : ReviewWebservice {
    override fun fetchAllReviews(offset: Int): Single<Pair<Int?, List<Review>>> {
        return reviewApi.fetchReviews(
            offset = offset,
            type = "all"
        )
            .map {
                Pair(
                    if (it.has_more)
                        offset + 20
                    else
                        null,
                    it.results.map { reviewData ->
                        reviewData.toReview()
                    }
                )
            }
    }

    override fun searchReviews(
        offset: Int,
        query: String,
    ): Single<Pair<Int?, List<Review>>> {
        return reviewApi.searchReviews(
            offset = offset,
            queryString = query
        )
            .map {
                Pair(
                    if (it.has_more)
                        offset + 20
                    else
                        null,
                    it.results.map { reviewData ->
                        reviewData.toReview()
                    }
                )
            }
    }
}