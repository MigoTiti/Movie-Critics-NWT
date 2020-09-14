package com.lucasrodrigues.moviereviews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasrodrigues.moviereviews.framework.AlertService
import com.lucasrodrigues.moviereviews.framework.NavigationService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val navigationService: NavigationService,
    protected val alertService: AlertService,
) : ViewModel() {

    protected val disposables = CompositeDisposable()

    fun request(
        completable: Completable,
        onRequestBegin: () -> Unit = {},
        onError: (error: Throwable) -> Unit = {},
        showErrorAlert: Boolean = true,
        errorObservable: MutableLiveData<Throwable>? = null,
        loadingObservable: MutableLiveData<Boolean>? = null,
        onRequestSuccess: () -> Unit = {}
    ) {
        disposables.add(
            completable
                .doOnSubscribe {
                    loadingObservable?.postValue(true)
                    errorObservable?.postValue(null)

                    onRequestBegin()
                }
                .subscribe({
                    loadingObservable?.postValue(false)
                    errorObservable?.postValue(null)

                    onRequestSuccess()
                }, {
                    loadingObservable?.postValue(false)
                    errorObservable?.postValue(it)

                    handleError(it, showErrorAlert)

                    onError(it)
                })
        )
    }

    private fun handleError(error: Throwable, showErrorAlert: Boolean) {
        error.printStackTrace()

        when {
            showErrorAlert -> {
                alertService.sendErrorAlert(error)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }
}
