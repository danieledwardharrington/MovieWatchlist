package com.dharringtondev.moviewatchlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.SearchAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentSearchBinding
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.remote.MovieModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory

class SearchFragment: Fragment(), SearchAdapter.OnMovieClickedListener {
    private val TAG = "SearchFragment"

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private val searchAdapter = SearchAdapter()
    private var searchedMovies = ArrayList<MovieModel>()
    private lateinit var searchedMovie: MovieModel
    private var watchedMovies = ArrayList<MovieEntity>()
    private var movieWatchlist = ArrayList<MovieEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        initViewModel()
        initRV()
        setupSearch()
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        movieViewModel.getRemoteMoviesList().observe(viewLifecycleOwner, Observer {
            searchedMovie = it
            val movies = arrayListOf<MovieModel>(it)
            searchAdapter.submitList(movies)
        })
    }

    private fun initRV() {
        binding.resultsRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
            searchAdapter.setMovieClickedListener(this@SearchFragment)
        }
    }

    private fun setupSearch () {
        binding.movieSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                movieViewModel.getRemoteMovies(query!!.trim())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieViewModel.getRemoteMovies(newText!!)
                return false
            }
        })
    }

    override fun onMovieClicked(movie: MovieModel) {
        movieViewModel.setMovie(movieViewModel.modelToEntity(movie))
        findNavController().navigate(R.id.fullMovieDialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}