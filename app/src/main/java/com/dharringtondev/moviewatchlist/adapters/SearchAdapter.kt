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
import com.dharringtondev.moviewatchlist.remote.omdb.MovieModel

class SearchAdapter: PagingDataAdapter<MovieModel, SearchAdapter.SearchViewHolder>(COMPARATOR) {

    private val TAG = "SearchAdapter"

    private lateinit var clickedListener: OnMovieClickedListener

    interface OnMovieClickedListener {
        fun onMovieClicked(movie: MovieModel)
    }

    fun setMovieClickedListener(newListener: OnMovieClickedListener) {
        clickedListener = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = CardViewSearchedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return SearchViewHolder(itemBinding, context)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        when (holder) {
            is SearchViewHolder -> {
                getItem(position).let {
                    if (it != null) {
                        holder.bind(it)
                    }
                }
                holder.itemView.setOnClickListener {
                    Log.d(TAG, "Movie clicked")
                    getItem(position).let { it1 ->
                        if (it1 != null) {
                            clickedListener.onMovieClicked(it1)
                        }
                    }
                }
            }
        }
    }

    fun getItemAtPosition(position: Int): MovieModel? {
        return getItem(position)
    }

    class SearchViewHolder(itemBinding: CardViewSearchedMovieBinding, parentContext: Context): RecyclerView.ViewHolder(itemBinding.root) {
        private val TAG = "SearchViewHolder"

        private val context = parentContext
        private val titleTV = itemBinding.movieTitleTv
        private val yearTV = itemBinding.yearTv
        private val posterIV = itemBinding.posterIv

        fun bind(movieModel: MovieModel) {
            Log.d(TAG, "BIND")
            titleTV.text = movieModel.getTitle()
            yearTV.text = movieModel.getYear()

            //loading poster with Glide
            Glide.with(context)
                .load(movieModel.getPosterUrl())
                .placeholder(R.drawable.ic_poster_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(posterIV)
        }
    }

    companion object {
        private val COMPARATOR = object: DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.getImdbId() == newItem.getImdbId()
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}