package com.lucasrodrigues.moviereviews.view

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.databinding.ActivityReviewDetailsBinding
import com.lucasrodrigues.moviereviews.viewmodel.ReviewDetailsViewModel
import kotlinx.android.synthetic.main.activity_review_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


@ExperimentalPagingApi
class ReviewDetailsActivity : BaseActivity<ActivityReviewDetailsBinding, ReviewDetailsViewModel>() {

    override val viewModel by viewModel<ReviewDetailsViewModel> {
        parametersOf(
            this as Activity,
            intent.getParcelableExtra("review")
        )
    }

    override val layoutId = R.layout.activity_review_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        constraintLayout.post {
            constraintLayout.transitionToEnd()
        }

        title = viewModel.initialReview.movieTitle

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.review.observe(this) {
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.review_details_menu, menu)

        val review = viewModel.review.value

        if (review != null) {
            menu?.findItem(R.id.action_toggle_favorite)?.setIcon(
                if (review.isFavorite)
                    R.drawable.ic_favorite
                else
                    R.drawable.ic_not_favorite
            )
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_toggle_favorite -> viewModel.toggleFavorite()
            R.id.action_share_review -> viewModel.shareReview()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, R.anim.exit_slide_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}