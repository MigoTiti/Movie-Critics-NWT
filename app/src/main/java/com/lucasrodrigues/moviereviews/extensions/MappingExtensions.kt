package com.lucasrodrigues.moviereviews.extensions

import com.lucasrodrigues.moviereviews.dataaccess.network.response.data.ReviewData
import com.lucasrodrigues.moviereviews.model.Review
import java.text.SimpleDateFormat

fun ReviewData.toReview(): Review {
    return Review(
        movieTitle = this.display_title,
        summary = this.summary_short,
        thumbnailUrl = this.multimedia?.src ?: "",
        isCriticsPick = this.critics_pick == 1,
        author = this.byline,
        articleUrl = this.link.url,
        articleTitle = this.link.suggested_link_text,
        headline = this.headline,
        openingDate = if (this.opening_date != null)
            SimpleDateFormat("yyyy-MM-dd").parse(this.opening_date)
        else
            null
    )
}