package com.lucasrodrigues.moviereviews.dataaccess.network.response

open class BaseResponse {

    var fault: ErrorData? = null

    data class ErrorData(
        val faultstring: String
    )
}