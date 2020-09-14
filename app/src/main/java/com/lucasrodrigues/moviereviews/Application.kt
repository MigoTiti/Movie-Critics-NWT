package com.lucasrodrigues.moviereviews

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.dependencies.*
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalPagingApi
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    DataAccessDependencies.module,
                    FrameworkDependencies.module,
                    RepositoryDependencies.module,
                    ViewModelDependencies.module,
                    WebserviceDependencies.module,
                )
            )
        }

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
    }
}
