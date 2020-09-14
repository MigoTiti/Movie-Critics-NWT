package com.lucasrodrigues.moviereviews.framework.impl

import android.content.Context
import androidx.core.content.ContextCompat
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.framework.ResourceService

class ResourceServiceImpl(
    private val context: Context
) : ResourceService {

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    override fun getQuantityString(id: Int, count: Int, vararg args: Any): String {
        return context.resources.getQuantityString(id, count, *args)
    }

    override fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    override fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            else -> exception.localizedMessage ?: exception.message
            ?: getString(R.string.error_unknown)
        }
    }
}
