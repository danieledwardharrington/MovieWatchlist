package com.dharringtondev.moviewatchlist.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var allMovies = ArrayList<MovieEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        initViewModel()
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        setupSearch()
        initRV()
        movieViewModel.getAllMovies()
        movieViewModel.getRemoteMoviesList().observe(viewLifecycleOwner, Observer {
            if(it != null) {
                searchedMovies = ArrayList(it)
                searchAdapter.submitList(searchedMovies)
            }
        })

        movieViewModel.getAllMoviesList().observe(viewLifecycleOwner, Observer {
            allMovies = ArrayList(it)
        })
    }

    private fun initRV() {
        binding.resultsRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
            searchAdapter.setMovieClickedListener(this@SearchFragment)
        }

        //swiping right to add to watchlist
        val itemRight = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movieModel = searchAdapter.getSearchedList()[viewHolder.adapterPosition]
                movieViewModel.getRemoteMovieById(movieModel.getImdbId())
                var movie = MovieModel()
                movieViewModel.getRemoteMovieByIdLiveData().observe(viewLifecycleOwner, Observer {
                    movie = it
                    val movieEntity = movieViewModel.modelToEntity(movie)
                    movieViewModel.insert(movieEntity)
                    showShortToast("Movie added to watchlist")
                })
                searchAdapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelperRight = ItemTouchHelper(itemRight)
        itemTouchHelperRight.attachToRecyclerView(binding.resultsRv)
    }

    private fun setupSearch () {
        binding.movieSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit; query = $query")
                movieViewModel.getRemoteMovies(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange; newText = $newText")
                if(TextUtils.isEmpty(newText)) {
                    searchAdapter.removeAll()
                } else {
                    movieViewModel.getRemoteMovies(newText!!)
                }
                return false
            }
        })
    }

    //when the user clicks a movie card, a dialog will appear to show more details
    override fun onMovieClicked(movie: MovieModel) {
        val action = SearchFragmentDirections.actionSearchFragmentToFullMovieDialog(movie.getImdbId())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}