package com.lucasrodrigues.moviereviews.dataaccess.network.response

import com.lucasrodrigues.moviereviews.dataaccess.network.response.data.ReviewData

data class FetchReviewsResponse(
    val status: String,
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val results: List<ReviewData>
) : BaseResponse()