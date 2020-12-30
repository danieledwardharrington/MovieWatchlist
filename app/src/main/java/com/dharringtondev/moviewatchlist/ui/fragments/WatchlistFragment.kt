package com.dharringtondev.moviewatchlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        initRV()
        initViewModel()
    }

    private fun initRV() {
        binding.watchlistRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = watchlistAdapter
        }
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        movieViewModel.getAllMovies()
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

    override fun onMovieClicked(movie: MovieEntity) {
        val action = WatchlistFragmentDirections.actionWatchlistFragmentToFullMovieDialog(movie.getImdbId())
        findNavController().navigate(action)
    }
}