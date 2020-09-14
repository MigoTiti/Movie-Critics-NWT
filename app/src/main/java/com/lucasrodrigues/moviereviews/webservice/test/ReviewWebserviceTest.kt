package com.lucasrodrigues.moviereviews.webservice.test

import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.webservice.ReviewWebservice
import io.reactivex.rxjava3.core.Single
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ReviewWebserviceTest : ReviewWebservice {

    private val reviews = (0..500).toList().map {
        Review(
            movieTitle = "movie$it",
            headline = "title$it",
            articleTitle = "articleTitle$it",
            articleUrl = "https://google.com",
            author = "Eu mesmo",
            isCriticsPick = Random.nextBoolean(),
            openingDate = Date(),
            summary = "Summary $it",
            thumbnailUrl = "https://static01.nyt.com/images/2020/09/11/arts/alive1/alive1-mediumThreeByTwo210.jpg",
        )
    }

    override fun fetchAllReviews(offset: Int): Single<Pair<Int?, List<Review>>> {
        return Single.fromCallable {
            val newList = reviews

            val hasNewPage = newList.size - offset > 20

            Pair(
                if (hasNewPage) offset + 20 else null,
                newList.subList(offset, newList.size).take(20)
            )
        }
            .delay(2, TimeUnit.SECONDS)
    }

    override fun searchReviews(
        offset: Int,
        query: String,
    ): Single<Pair<Int?, List<Review>>> {
        return Single.fromCallable {
            val newList = reviews.filter {
                it.movieTitle.contains(query, ignoreCase = true)
            }

            val hasNewPage = newList.size - offset > 20

            Pair(
                if (hasNewPage) offset + 20 else null,
                newList.subList(offset, newList.size).take(20)
            )
        }
            .delay(2, TimeUnit.SECONDS)
    }
}