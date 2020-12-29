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
import com.dharringtondev.moviewatchlist.remote.MovieModel

class SearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "SearchAdapter"

    private var movieList = ArrayList<MovieModel>()
    private lateinit var clickedListener: OnMovieClickedListener

    interface OnMovieClickedListener {
        fun onMovieClicked(movie: MovieModel)
    }

    fun setMovieClickedListener(newListener: OnMovieClickedListener) {
        clickedListener = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = CardViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return SearchViewHolder(itemBinding, context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        when (holder) {
            is SearchViewHolder -> {
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

    fun submitList(newList: ArrayList<MovieModel>) {
        Log.d(TAG, "submitList; newList length = ${newList.size}")
        movieList = newList
    }

    private fun add(newMovie: MovieModel) {
        movieList.add(newMovie)
        notifyDataSetChanged()
    }

    private fun removeAt(position: Int) {
        movieList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, movieList.size)
    }

    class SearchViewHolder(itemBinding: CardViewMovieBinding, parentContext: Context): RecyclerView.ViewHolder(itemBinding.root) {
        private val TAG = "SearchViewHolder"

        private val context = parentContext
        private val titleTV = itemBinding.movieTitleTv
        private val yearTV = itemBinding.yearTv
        private val directorTV = itemBinding.directorTv
        private val startsTV = itemBinding.starsTv
        private val watchedTV = itemBinding.watchedTv
        private val posterIV = itemBinding.posterIv

        fun bind(movieModel: MovieModel) {
            Log.d(TAG, "BIND")
            titleTV.text = movieModel.getTitle()
            yearTV.text = movieModel.getYear()
            directorTV.text = movieModel.getDirector()
            startsTV.text = movieModel.getActors()

            //loading poster with Glide
            Glide.with(context)
                .load(movieModel.getPosterUrl())
                .placeholder(R.drawable.ic_poster_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(posterIV)
        }
    }

}