package com.lucasrodrigues.moviereviews.view

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.components.adapter.PagingLoadStateAdapter
import com.lucasrodrigues.moviereviews.components.adapter.ReviewsAdapter
import com.lucasrodrigues.moviereviews.databinding.ActivityMainBinding
import com.lucasrodrigues.moviereviews.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalPagingApi
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel by viewModel<MainViewModel> { parametersOf(this as Activity) }
    override val layoutId = R.layout.activity_main

    private val allReviewsAdapter = ReviewsAdapter(
        navigationService = navigationService,
        layoutId = R.layout.item_vertical_review
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allReviewsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allReviewsAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter(R.layout.item_vertical_loading_state) {
                    allReviewsAdapter.retry()
                }
            )

            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        disposables.addAll(
            viewModel.getAllReviews().subscribe {
                allReviewsAdapter.submitData(lifecycle, it)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView?)?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isQueryRefinementEnabled = true

            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val selectedView: CursorAdapter = suggestionsAdapter
                    val cursor: Cursor = selectedView.getItem(position) as Cursor
                    val index: Int = cursor.getColumnIndexOrThrow(
                        SearchManager.SUGGEST_COLUMN_TEXT_1
                    )
                    setQuery(cursor.getString(index), true)
                    return true
                }
            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_favorites -> navigationService.goToFavoriteReviews()
        }

        return super.onOptionsItemSelected(item)
    }
}