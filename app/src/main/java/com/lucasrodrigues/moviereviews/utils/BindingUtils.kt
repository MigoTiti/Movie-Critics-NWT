package com.lucasrodrigues.moviereviews.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lucasrodrigues.moviereviews.R

object BindingUtils {

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url ?: R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, value: Boolean?) {
        view.isVisible = value == true
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibilityNew(view: View, value: Boolean?) {
        if (value == true)
            view.isVisible = true
        else
            view.isInvisible = true
    }
}