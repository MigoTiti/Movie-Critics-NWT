package com.lucasrodrigues.moviereviews.dataaccess.network

import com.lucasrodrigues.moviereviews.BuildConfig
import com.lucasrodrigues.moviereviews.dataaccess.network.response.FetchReviewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApi {

    @GET("reviews/{type}.json")
    fun fetchReviews(
        @Path("type") type: String,
        @Query("api-key") apiKey: String = BuildConfig.NWT_API_KEY,
        @Query("offset") offset: Int = 0,
        @Query("order") orderBy: String? = null,
    ): Single<FetchReviewsResponse>

    @GET("reviews/search.json")
    fun searchReviews(
        @Query("api-key") apiKey: String = BuildConfig.NWT_API_KEY,
        @Query("critics_pick") type: String? = null,
        @Query("offset") offset: Int = 0,
        @Query("order") orderBy: String? = null,
        @Query("opening-date") openingDate: String? = null,
        @Query("publication-date") publicationDate: String? = null,
        @Query("reviewer") reviewerName: String? = null,
        @Query("query") queryString: String? = null,
    ): Single<FetchReviewsResponse>
}