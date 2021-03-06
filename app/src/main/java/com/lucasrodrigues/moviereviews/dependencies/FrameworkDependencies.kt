package com.lucasrodrigues.moviereviews.dependencies

import android.app.Activity
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.framework.AlertService
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.framework.ResourceService
import com.lucasrodrigues.moviereviews.framework.impl.AlertServiceImpl
import com.lucasrodrigues.moviereviews.framework.impl.NavigationServiceImpl
import com.lucasrodrigues.moviereviews.framework.impl.ResourceServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@ExperimentalPagingApi
object FrameworkDependencies {
    val module = module(override = true) {
        single<ResourceService> { ResourceServiceImpl(androidContext().applicationContext) }

        factory<NavigationService> { (activity: Activity) -> NavigationServiceImpl(activity) }

        factory<AlertService> { (activity: Activity) ->
            AlertServiceImpl(
                resourceService = get(),
                activity = activity
            )
        }
    }
}