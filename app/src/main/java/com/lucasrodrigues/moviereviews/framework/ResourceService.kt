package com.lucasrodrigues.moviereviews.framework

interface ResourceService {
    fun getString(id: Int, vararg args: Any): String
    fun getErrorMessage(exception: Throwable): String
    fun getColor(id: Int): Int
	fun getQuantityString(id: Int, count: Int, vararg args: Any): String
}
