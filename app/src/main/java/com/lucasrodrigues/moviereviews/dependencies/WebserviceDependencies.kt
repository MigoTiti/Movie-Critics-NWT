package com.lucasrodrigues.moviereviews.dependencies

import com.lucasrodrigues.moviereviews.BuildConfig
import com.lucasrodrigues.moviereviews.webservice.ReviewWebservice
import com.lucasrodrigues.moviereviews.webservice.impl.ReviewWebserviceImpl
import com.lucasrodrigues.moviereviews.webservice.test.ReviewWebserviceTest
import org.koin.dsl.module

object WebserviceDependencies {
    val module = module(override = true) {
        if (BuildConfig.MOCK_DATA) {
            single<ReviewWebservice> { ReviewWebserviceTest() }
        } else {
            single<ReviewWebservice> {
                ReviewWebserviceImpl(
                    reviewApi = get()
                )
            }
        }
    }
}