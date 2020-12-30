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
import com.dharringtondev.moviewatchlist.databinding.FragmentWatchedBinding
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory

class WatchedFragment: Fragment(), MovieAdapter.OnMovieClickedListener {
    private val TAG = "WatchedFragment"

    private var _binding: FragmentWatchedBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private val watchedAdapter = MovieAdapter()
    private var watchedMovies = ArrayList<MovieEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWatchedBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        initRV()
        initViewModel()
    }

    private fun initRV() {
        binding.watchedRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = watchedAdapter
            watchedAdapter.setMovieClickedListener(this@WatchedFragment)
        }

        /*
        Swipe actions
        Swipe left: Delete from the list. Mostly for if it's here by mistake.
        Swipe right: Put back on watchlist.
         */
        val itemLeft = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movieEntity = watchedAdapter.getMovieList()[viewHolder.adapterPosition]
                movieViewModel.delete(movieEntity)
                watchedAdapter.removeAt(viewHolder.adapterPosition)
                showShortToast("Movie deleted from list")
            }
        }

        val itemRight = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movieEntity = watchedAdapter.getMovieList()[viewHolder.adapterPosition]
                movieEntity.setWatched(false)
                movieViewModel.update(movieEntity)
                watchedAdapter.removeAt(viewHolder.adapterPosition)
                showShortToast("Movie moved to watchlist")
            }
        }

        val itemTouchHelperLeft = ItemTouchHelper(itemLeft)
        val itemTouchHelperRight = ItemTouchHelper(itemRight)
        itemTouchHelperLeft.attachToRecyclerView(binding.watchedRv)
        itemTouchHelperRight.attachToRecyclerView(binding.watchedRv)
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        movieViewModel.getAllMovies()
        movieViewModel.getWatchedMoviesList().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                watchedMovies = ArrayList(it)
                watchedAdapter.submitList(watchedMovies)
            }
        })
    }

    //laucnhing dialog with more details about the movie
    override fun onMovieClicked(movie: MovieEntity) {
        val action = WatchedFragmentDirections.actionWatchedFragmentToFullMovieDialog(movie.getImdbId())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.getAllMovies()
    }

    private fun showShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}