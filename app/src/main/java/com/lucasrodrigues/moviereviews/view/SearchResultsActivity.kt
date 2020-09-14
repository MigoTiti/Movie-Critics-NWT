package com.lucasrodrigues.moviereviews.view

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasrodrigues.moviereviews.R
import com.lucasrodrigues.moviereviews.components.SuggestionProvider
import com.lucasrodrigues.moviereviews.components.adapter.PagingLoadStateAdapter
import com.lucasrodrigues.moviereviews.components.adapter.ReviewsAdapter
import com.lucasrodrigues.moviereviews.databinding.ActivitySearchResultsBinding
import com.lucasrodrigues.moviereviews.viewmodel.SearchResultsViewModel
import kotlinx.android.synthetic.main.activity_search_results.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalPagingApi
class SearchResultsActivity : BaseActivity<ActivitySearchResultsBinding, SearchResultsViewModel>() {

    override val viewModel by viewModel<SearchResultsViewModel> {
        parametersOf(
            this as Activity,
            intent.getStringExtra(SearchManager.QUERY) ?: ""
        )
    }

    override val layoutId = R.layout.activity_search_results

    private val searchResultsAdapter = ReviewsAdapter(
        navigationService = navigationService,
        layoutId = R.layout.item_vertical_review
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(
                    this,
                    SuggestionProvider.AUTHORITY,
                    SuggestionProvider.MODE
                )
                    .saveRecentQuery(query, null)
            }
        }

        searchResultsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultsAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter(R.layout.item_vertical_loading_state) {
                    searchResultsAdapter.retry()
                }
            )

            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        disposables.add(
            viewModel.getSearchResults().subscribe {
                searchResultsAdapter.submitData(lifecycle, it)
            }
        )

        title = viewModel.queryString

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}