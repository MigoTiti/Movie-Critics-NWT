package com.lucasrodrigues.moviereviews.dataaccess.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lucasrodrigues.moviereviews.model.Review
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ReviewDao : BaseDao<Review> {

    @Query("SELECT * FROM Review")
    fun selectAllReviews(): PagingSource<Int, Review>

    @Query("SELECT * FROM Review WHERE movieTitle LIKE '%' || :query  || '%'")
    fun selectReviewsUsingQuery(query: String): PagingSource<Int, Review>

    @Query("DELETE FROM Review")
    fun clear()

    @Query("SELECT * FROM Review WHERE movieTitle == :id")
    fun selectReviewById(id: String): Review?

    @Query("SELECT * FROM Review WHERE movieTitle == :id")
    fun selectReviewByIdSynchronized(id: String): Flowable<Review>

    @Query("SELECT * FROM Review WHERE is_favorite == 1")
    fun selectFavoriteReviewsSynchronized(): Flowable<List<Review>>

    @Transaction
    fun toggleFavoriteFlag(review: Review) {
        updateSynchronous(review.copy(isFavorite = !review.isFavorite))
    }

    @Transaction
    fun insertReviewsPreservingFavoriteFlag(vararg reviews: Review) {
        reviews.forEach {
            val currentItem = selectReviewById(it.movieTitle)

            if (currentItem != null) {
                updateSynchronous(it.copy(isFavorite = currentItem.isFavorite))
            } else {
                insertSynchronous(it)
            }
        }
    }
}