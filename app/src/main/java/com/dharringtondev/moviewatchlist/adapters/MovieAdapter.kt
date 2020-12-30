package com.dharringtondev.moviewatchlist.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.CardViewMovieBinding
import com.dharringtondev.moviewatchlist.persistence.MovieEntity

class MovieAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "MovieAdapter"

    private var movieList = ArrayList<MovieEntity>()
    private lateinit var clickedListener: OnMovieClickedListener

    interface OnMovieClickedListener {
        fun onMovieClicked(movie: MovieEntity)
    }

    fun setMovieClickedListener(newListener: OnMovieClickedListener) {
        clickedListener = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = CardViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return MovieViewHolder(itemBinding, context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(movieList[position])
                holder.itemView.setOnClickListener {
                    Log.d(TAG, "Movie clicked")
                    clickedListener.onMovieClicked(movieList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return movieList.size
    }

    fun submitList(newList: ArrayList<MovieEntity>) {
        Log.d(TAG, "submitList; newList length = ${newList.size}")
        movieList = newList
    }

    private fun add(newMovie: MovieEntity) {
        movieList.add(newMovie)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        movieList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, movieList.size)
    }

    fun getMovieList(): ArrayList<MovieEntity> {
        return movieList
    }

    class MovieViewHolder(itemBinding: CardViewMovieBinding, parentContext: Context): RecyclerView.ViewHolder(itemBinding.root) {
        private val TAG = "MovieViewHolder"

        private val context = parentContext
        private val titleTV = itemBinding.movieTitleTv
        private val yearTV = itemBinding.yearTv
        private val directorTV = itemBinding.directorTv
        private val startsTV = itemBinding.starsTv
        private val watchedTV = itemBinding.watchedTv
        private val posterIV = itemBinding.posterIv

        fun bind(movieEntity: MovieEntity) {
            Log.d(TAG, "BIND")
            titleTV.text = movieEntity.getTitle()
            yearTV.text = movieEntity.getYear()
            directorTV.text = movieEntity.getDirector()
            startsTV.text = movieEntity.getActors()
            if (movieEntity.getWatched()) {
                watchedTV.visibility = View.VISIBLE
            }

            //loading poster with Glide
            Glide.with(context)
                .load(movieEntity.getPosterUrl())
                .placeholder(R.drawable.ic_poster_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(posterIV)
        }
    }
}