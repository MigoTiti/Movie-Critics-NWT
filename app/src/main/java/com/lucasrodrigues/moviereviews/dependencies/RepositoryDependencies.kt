package com.lucasrodrigues.moviereviews.dependencies

import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.repository.ReviewRepository
import org.koin.dsl.module

@ExperimentalPagingApi
object RepositoryDependencies {
    val module = module(override = true) {
        single {
            ReviewRepository(
                reviewDao = get(),
                reviewWebservice = get()
            )
        }
    }
}