package com.dharringtondev.moviewatchlist.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.SearchAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentSearchBinding
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.remote.omdb.models.MovieModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SearchFragment: Fragment(), SearchAdapter.OnMovieClickedListener {
    private val TAG = "SearchFragment"

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private val searchAdapter = SearchAdapter()
    private var allMovies = ArrayList<MovieEntity>()

    //handling whether or not to show tutorial
    private val TUTORIAL_PREF = "TutorialPref"
    private val TUTORIAL_BOOL_KEY = "TutorialBool"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        prepareTutorial()
    }

    private fun initComponents() {
        initViewModel()
    }

    private fun initViewModel() {
        Log.d(TAG, "initViewModel")
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        movieViewModel.tutorialSeenLD.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "tutorialSeenLD observe; seen = $it")
            saveTutorialSeen()
        })
        Log.d(TAG, "internet connected")
        setupSearch()
        initRV()
        if (isOnline(requireContext())) {
            movieViewModel.getAllMovies()
            movieViewModel.getRemoteMoviesList().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    searchAdapter.submitData(lifecycle, it)
                }
            })

            movieViewModel.getAllMoviesList().observe(viewLifecycleOwner, Observer {
                allMovies = ArrayList(it)
            })

            movieViewModel.getRemoteMovieByIdLiveData().observe(viewLifecycleOwner, Observer {
                val movie = it
                val movieEntity = movieViewModel.modelToEntity(movie)
                movieViewModel.insert(movieEntity)
                movieViewModel.getRemoteMoviesList().value?.let { it1 ->
                    searchAdapter.submitData(lifecycle, it1.filter {
                        Log.d(TAG, "submitData filter")
                        !movieViewModel.getSwipedMovieIds().contains(it.getImdbId())
                    })
                }
                showShortToast("Movie added to watchlist")
            })

        } else {
            Log.e(TAG, "no internet")
            showLongSnackbar("No internet connection")
        }
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
                val movieModel: MovieModel = searchAdapter.getItemAtPosition(viewHolder.bindingAdapterPosition) as MovieModel
                movieViewModel.getSwipedMovieIds().add(movieModel.getImdbId())
                movieViewModel.getRemoteMovieById(movieModel.getImdbId())
            }
        }

        val itemTouchHelperRight = ItemTouchHelper(itemRight)
        itemTouchHelperRight.attachToRecyclerView(binding.resultsRv)
    }

    //this function handles whether or not we're showing the tutorial to the user
    private fun prepareTutorial() {
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.searchFragment)

        // Create our observer and add it to the NavBackStackEntry's lifecycle
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME
                && navBackStackEntry.savedStateHandle.contains(TUTORIAL_BOOL_KEY)) {
                val result = navBackStackEntry.savedStateHandle.get<Boolean>(TUTORIAL_BOOL_KEY);
                if (result!!) {
                    saveTutorialSeen()
                }
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })

        if (!getTutorialSeen()) {
            findNavController().navigate(R.id.tutorialDialog)
        }
    }

    private fun setupSearch() {
        binding.movieSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit; query = $query")
                if (isOnline(requireContext())) {
                    movieViewModel.getRemoteMoviesWithPage(query!!)
                } else {
                    showLongSnackbar("No internet connection")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange; newText = $newText")
                if(isOnline(requireContext())) {
                    if (TextUtils.isEmpty(newText)) {
                        searchAdapter.submitData(lifecycle, PagingData.empty())
                    } else {
                        movieViewModel.getRemoteMoviesWithPage(newText!!)
                    }
                } else {
                    showLongSnackbar("No internet connection")
                }
                return false
            }
        })
    }

    private fun getTutorialSeen(): Boolean {
        Log.d(TAG, "getTutorialSeen")
        val prefs = requireActivity().getSharedPreferences(TUTORIAL_PREF, Context.MODE_PRIVATE)
        val seen = prefs.getBoolean(TUTORIAL_BOOL_KEY, false)
        Log.d(TAG, "getTutorialSeen; seen = $seen")
        return seen
    }

    private fun saveTutorialSeen() {
        Log.d(TAG, "saveTutorialSeen")
        val prefs = requireActivity().getSharedPreferences(TUTORIAL_PREF, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(TUTORIAL_BOOL_KEY, true).apply()
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

    private fun showLongSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        if (!isOnline(requireContext())) {
            showLongSnackbar("No internet connection")
        }
    }
}