package com.lucasrodrigues.moviereviews.view

import android.app.Activity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.components.epoxy.FavoriteListController
import com.lucasrodrigues.moviereviews.databinding.ActivityFavoriteReviewsBinding
import com.lucasrodrigues.moviereviews.viewmodel.FavoriteReviewsViewModel
import kotlinx.android.synthetic.main.activity_favorite_reviews.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalPagingApi
class FavoriteReviewsActivity :
    BaseActivity<ActivityFavoriteReviewsBinding, FavoriteReviewsViewModel>() {

    override val viewModel by viewModel<FavoriteReviewsViewModel> { parametersOf(this as Activity) }

    override val layoutId = R.layout.activity_favorite_reviews

    private val favoriteListController = FavoriteListController(
        navigationService,
        resourceService
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteReviewsRv.apply {
            setController(favoriteListController)

            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        viewModel.favoriteReviews.observe(this) {
            favoriteListController.setData(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}