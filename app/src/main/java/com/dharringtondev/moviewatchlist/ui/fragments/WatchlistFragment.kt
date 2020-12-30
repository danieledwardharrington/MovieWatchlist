package com.dharringtondev.moviewatchlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.MovieAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentWatchlistBinding
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory

class WatchlistFragment: Fragment(), MovieAdapter.OnMovieClickedListener {
    private val TAG = "WatchlistFragment"

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private val watchlistAdapter = MovieAdapter()
    private var watchlist = ArrayList<MovieEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        initViewModel()
    }

    private fun initRV() {
        binding.watchlistRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = watchlistAdapter
            watchlistAdapter.setMovieClickedListener(this@WatchlistFragment)
        }

        /*
        Swiping
        Swipe left: Delete the movie. Meaning the user no longer wants to watch it.
        Swipe right: Move the movie to watched, a separate list, and remove it from watchlist.
         */
        val itemLeft = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movieEntity = watchlistAdapter.getMovieList()[viewHolder.adapterPosition]
                movieViewModel.delete(movieEntity)
                watchlistAdapter.removeAt(viewHolder.adapterPosition)
                showShortToast("Movie deleted from watchlist")
            }
        }

        val itemRight = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movieEntity = watchlistAdapter.getMovieList()[viewHolder.adapterPosition]
                movieEntity.setWatched(true)
                movieViewModel.update(movieEntity)
                watchlistAdapter.removeAt(viewHolder.adapterPosition)
                showShortToast("Movie marked as watched")
            }
        }

        val itemTouchHelperLeft = ItemTouchHelper(itemLeft)
        val itemTouchHelperRight = ItemTouchHelper(itemRight)
        itemTouchHelperLeft.attachToRecyclerView(binding.watchlistRv)
        itemTouchHelperRight.attachToRecyclerView(binding.watchlistRv)
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        movieViewModel.getAllMovies()
        initRV()
        movieViewModel.getWatchlist().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                watchlist = ArrayList(it)
                watchlistAdapter.submitList(watchlist)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //launching dialog with details about the movie
    override fun onMovieClicked(movie: MovieEntity) {
        val action = WatchlistFragmentDirections.actionWatchlistFragmentToFullMovieDialog(movie.getImdbId())
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.getAllMovies()
    }

    private fun showShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}