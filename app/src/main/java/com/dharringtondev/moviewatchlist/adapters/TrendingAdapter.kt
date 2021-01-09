package com.dharringtondev.moviewatchlist.adapters

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dharringtondev.moviewatchlist.remote.tmdb.TmdbMovieModel

class TrendingAdapter: PagingDataAdapter<TmdbMovieModel, TrendingAdapter.TrendingViewHolder>(
    COMPARATOR) {

    companion object {
        private val COMPARATOR = object: DiffUtil.ItemCallback<TmdbMovieModel>() {
            override fun areItemsTheSame(oldItem: TmdbMovieModel, newItem: TmdbMovieModel): Boolean {
                return oldItem.getTmdbId() == newItem.getTmdbId()
            }

            override fun areContentsTheSame(oldItem: TmdbMovieModel, newItem: TmdbMovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}