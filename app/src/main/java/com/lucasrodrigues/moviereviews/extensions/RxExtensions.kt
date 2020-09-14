package com.lucasrodrigues.moviereviews.extensions

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.runFullyOnBackground(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}

fun Completable.runFullyOnBackground(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}