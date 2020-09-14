package com.lucasrodrigues.moviereviews.components

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.lucasrodrigues.moviereviews.model.Review
import io.reactivex.rxjava3.core.Single

@ExperimentalPagingApi
class ReviewRemoteMediator(
    private val fetchNextPage: (offset: Int) -> Single<Pair<Int?, List<Review>>>
) : RxRemoteMediator<Int, Review>() {

    private var endOfList = false
    private var nextOffset: Int? = null

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Review>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .map {
                if (it == LoadType.REFRESH)
                    0
                else {
                    nextOffset ?: state.pages.last().prevKey
                        ?: state.pages.sumBy { page ->
                            page.data.size
                        }
                }
            }
            .flatMap { offset ->
                if (offset == -1 || loadType == LoadType.PREPEND || endOfList) {
                    Single.just(
                        MediatorResult.Success(endOfPaginationReached = true)
                    )
                } else {
                    fetchNextPage(offset)
                        .map<MediatorResult> {
                            endOfList = it.first == null
                            nextOffset = it.first

                            MediatorResult.Success(
                                endOfPaginationReached = it.first == null
                            )
                        }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }
    }
}