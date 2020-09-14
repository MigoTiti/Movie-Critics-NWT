package com.lucasrodrigues.moviereviews.dataaccess.network.response.data

data class ReviewData(
    val byline: String,
    val critics_pick: Int,
    val date_updated: String,
    val display_title: String,
    val headline: String,
    val link: LinkData,
    val mpaa_rating: String,
    val multimedia: MultimediaData?,
    val opening_date: String?,
    val publication_date: String,
    val summary_short: String,
)