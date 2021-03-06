package com.lucasrodrigues.moviereviews.framework.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.moviereviews.framework.NavigationService
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.view.FavoriteReviewsActivity
import com.lucasrodrigues.moviereviews.view.ReviewDetailsActivity
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.newTask
import org.jetbrains.anko.singleTop
import java.io.Serializable
import java.lang.ref.WeakReference

@ExperimentalPagingApi
class NavigationServiceImpl(
    activity: Activity
) : NavigationService {

    private val activity = WeakReference(activity)

    override fun shareReview(review: Review) {
        activity.get()?.let {
            with(it) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    putExtra(
                        Intent.EXTRA_TEXT,
                        "${review.articleTitle}\n\n${review.articleUrl}"
                    )

                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    override fun goToFavoriteReviews() {
        startActivity(FavoriteReviewsActivity::class.java)
    }

    override fun goToReviewDetails(review: Review) {
        startActivity(
            ReviewDetailsActivity::class.java,
            params = arrayOf("review" to review),
            noAnimation = true
        )
    }

    override fun goBack() {
        activity.get()?.let {
            with(it) {
                runOnUiThread {
                    finish()
                }
            }
        }
    }

    override fun goBackWithData(vararg params: Pair<String, Any?>) {
        activity.get()?.let {
            with(it) {
                runOnUiThread {
                    setResult(Activity.RESULT_OK, buildIntent(params = params))
                    finish()
                }
            }
        }
    }

    private fun buildIntent(
        activityClass: Class<*>? = null,
        context: Context? = null,
        vararg params: Pair<String, Any?>
    ): Intent {
        return (if (activityClass != null) {
            Intent(context, activityClass)
        } else {
            Intent()
        }).apply {
            params.forEach { pair ->
                when (val value = pair.second) {
                    null -> putExtra(pair.first, null as Serializable?)
                    is Int -> putExtra(pair.first, value)
                    is Long -> putExtra(pair.first, value)
                    is CharSequence -> putExtra(pair.first, value)
                    is String -> putExtra(pair.first, value)
                    is Float -> putExtra(pair.first, value)
                    is Double -> putExtra(pair.first, value)
                    is Char -> putExtra(pair.first, value)
                    is Short -> putExtra(pair.first, value)
                    is Boolean -> putExtra(pair.first, value)
                    is Serializable -> putExtra(pair.first, value)
                    is Bundle -> putExtra(pair.first, value)
                    is Parcelable -> putExtra(pair.first, value)
                    is Array<*> -> when {
                        value.isArrayOf<CharSequence>() -> putExtra(pair.first, value)
                        value.isArrayOf<String>() -> putExtra(pair.first, value)
                        value.isArrayOf<Parcelable>() -> putExtra(pair.first, value)
                    }
                    is IntArray -> putExtra(pair.first, value)
                    is LongArray -> putExtra(pair.first, value)
                    is FloatArray -> putExtra(pair.first, value)
                    is DoubleArray -> putExtra(pair.first, value)
                    is CharArray -> putExtra(pair.first, value)
                    is ShortArray -> putExtra(pair.first, value)
                    is BooleanArray -> putExtra(pair.first, value)
                }
            }
        }
    }

    private fun startActivity(
        activityClass: Class<*>,
        clearAll: Boolean = false,
        clearTop: Boolean = true,
        singleTop: Boolean = true,
        newTask: Boolean = false,
        requestCode: Int? = null,
        vararg params: Pair<String, Any?>,
        options: Bundle? = null,
        noAnimation: Boolean = false
    ) {
        activity.get()?.let {
            with(it) {
                runOnUiThread {
                    val intent = buildIntent(
                        activityClass = activityClass,
                        context = this,
                        params = params
                    ).apply {
                        if (singleTop)
                            singleTop()

                        if (clearTop)
                            clearTop()

                        if (newTask)
                            newTask()
                    }

                    if (requestCode == null)
                        startActivity(intent, options)
                    else
                        startActivityForResult(intent, requestCode, options)

                    if (clearAll)
                        finishAffinity()

                    if (noAnimation)
                        overridePendingTransition(0, 0)
                }
            }
        }
    }
}
