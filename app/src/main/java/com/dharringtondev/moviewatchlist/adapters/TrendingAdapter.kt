package com.dharringtondev.moviewatchlist.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.CardViewSearchedMovieBinding
import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbMovieModel

class TrendingAdapter: PagingDataAdapter<TmdbMovieModel, TrendingAdapter.TrendingViewHolder>(COMPARATOR) {

    private val TAG = "TrendingAdapter"

    private lateinit var clickedListener: OnTrendingMovieClickedListener

    interface OnTrendingMovieClickedListener {
        fun onTrendingMovieClicked(movie: TmdbMovieModel)
    }

    fun setTrendingMovieClickedListener(newListener: OnTrendingMovieClickedListener) {
        clickedListener = newListener
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        when (holder) {
            is TrendingViewHolder -> {
                getItem(position).let {
                    if (it != null) {
                        holder.bind(it)
                    }
                }
                holder.itemView.setOnClickListener {
                    Log.d(TAG, "Movie clicked")
                    getItem(position).let {
                        if (it != null) {
                            clickedListener.onTrendingMovieClicked(it)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = CardViewSearchedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return TrendingViewHolder(itemBinding, context)
    }

    class TrendingViewHolder(itemBinding: CardViewSearchedMovieBinding, parentContext: Context): RecyclerView.ViewHolder(itemBinding.root) {
        private val TAG = "TrendingViewHolder"

        private val context = parentContext
        private val titleTV = itemBinding.movieTitleTv
        private val yearTV = itemBinding.yearTv
        private val posterIV = itemBinding.posterIv

        fun bind(movie: TmdbMovieModel) {
            Log.d(TAG, "BIND")
            titleTV.text = movie.getTitle()
            yearTV.text = movie.getReleaseYear()

            //loading poster with Glide
            Glide.with(context)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_poster_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(posterIV)
        }

    }

    fun getItemAtPosition(position: Int): TmdbMovieModel? {
        return getItem(position)
    }

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