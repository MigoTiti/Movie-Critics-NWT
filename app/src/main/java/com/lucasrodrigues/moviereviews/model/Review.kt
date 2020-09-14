package com.lucasrodrigues.moviereviews.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Review(
    @PrimaryKey val movieTitle: String,
    @ColumnInfo(name = "article_url") val articleUrl: String,
    @ColumnInfo(name = "article_title") val articleTitle: String,
    @ColumnInfo(name = "headline") val headline: String,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "is_critics_pick") val isCriticsPick: Boolean,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "opening_date") val openingDate: Date?,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false,
) : Parcelable