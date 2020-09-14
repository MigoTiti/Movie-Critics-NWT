package com.lucasrodrigues.moviereviews.dependencies

import com.lucasrodrigues.moviereviews.BuildConfig
import com.lucasrodrigues.moviereviews.dataaccess.local.LocalDatabase
import com.lucasrodrigues.moviereviews.dataaccess.network.ReviewApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataAccessDependencies {
    val module = module(override = true) {
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(
                    OkHttpClient()
                        .newBuilder()
                        .readTimeout(2, TimeUnit.MINUTES)
                        .connectTimeout(2, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .callTimeout(2, TimeUnit.MINUTES)
                        .apply {
                            if (BuildConfig.DEBUG) {
                                addInterceptor(
                                    HttpLoggingInterceptor().apply {
                                        level = HttpLoggingInterceptor.Level.BODY
                                    }
                                )
                            }
                        }
                        .build()
                )
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single { get<Retrofit>().create(ReviewApi::class.java) }

        single { LocalDatabase.getInstance(androidContext().applicationContext) }

        single { get<LocalDatabase>().reviewDao() }
    }
}