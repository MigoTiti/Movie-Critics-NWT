package com.lucasrodrigues.moviereviews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.lucasrodrigues.moviereviews.components.ReviewRemoteMediator
import com.lucasrodrigues.moviereviews.dataaccess.local.dao.ReviewDao
import com.lucasrodrigues.moviereviews.extensions.runFullyOnBackground
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.webservice.ReviewWebservice
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@ExperimentalPagingApi
class ReviewRepository(
    private val reviewWebservice: ReviewWebservice,
    private val reviewDao: ReviewDao
) {
    fun listenToReview(id: String): LiveData<Review> {
        return LiveDataReactiveStreams.fromPublisher(reviewDao.selectReviewByIdSynchronized(id))
    }

    fun listenToFavoriteReviews(): LiveData<List<Review>> {
        return LiveDataReactiveStreams.fromPublisher(reviewDao.selectFavoriteReviewsSynchronized())
    }

    fun allReviewsPagedList(): Flowable<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            remoteMediator = ReviewRemoteMediator {
                fetchAllReviews(it)
            }
        ) {
            reviewDao.selectAllReviews()
        }.flowable
    }

    fun searchReviewsPagedList(query: String): Flowable<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            remoteMediator = ReviewRemoteMediator {
                searchReviews(it, query)
            }
        ) {
            reviewDao.selectReviewsUsingQuery(query)
        }.flowable
    }

    private fun fetchAllReviews(offset: Int): Single<Pair<Int?, List<Review>>> {
        return reviewWebservice.fetchAllReviews(
            offset = offset,
        )
            .flatMap {
                insertReviews(it.second).toSingle { it }
            }
            .runFullyOnBackground()
    }

    private fun searchReviews(offset: Int, query: String): Single<Pair<Int?, List<Review>>> {
        return reviewWebservice.searchReviews(
            offset = offset,
            query = query
        )
            .flatMap {
                insertReviews(it.second).toSingle { it }
            }
            .runFullyOnBackground()
    }

    fun toggleFavoriteReview(review: Review): Completable {
        return Completable.fromAction {
            reviewDao.toggleFavoriteFlag(review)
        }
            .runFullyOnBackground()
    }

    private fun insertReviews(reviews: List<Review>): Completable {
        return Completable.fromAction {
            reviewDao.insertReviewsPreservingFavoriteFlag(*reviews.toTypedArray())
        }
            .runFullyOnBackground()
    }
}