package com.lucasrodrigues.moviereviews.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.lucasrodrigues.moviereviews.components.adapter.viewholder.DataBindingViewHolder

class PagingLoadStateAdapter(
    private val layoutId: Int,
    private val retry: () -> Unit
) : LoadStateAdapter<DataBindingViewHolder>() {

    override fun onBindViewHolder(holder: DataBindingViewHolder, loadState: LoadState) {
        holder.bind(
            item = if (loadState is LoadState.Error) loadState.error else null
        ) {
            retry()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DataBindingViewHolder {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }
}