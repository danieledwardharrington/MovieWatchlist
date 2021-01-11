package com.dharringtondev.moviewatchlist.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.adapters.TrendingAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentTrendingBinding
import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbMovieModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory
import com.google.android.material.snackbar.Snackbar

class TrendingFragment: Fragment(), TrendingAdapter.OnTrendingMovieClickedListener {
    private val TAG = "TrendingFragment"

    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private val trendingAdapter = TrendingAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        if(context?.let { isOnline(it) } == true) {
            initComponents()
        } else {
            showLongSnackbar("No internet connection")
        }
    }

    private fun initComponents() {
        Log.d(TAG, "initComponents")
        initViewModel()
        initRV()
    }

    private fun initViewModel() {
        Log.d(TAG, "initViewModel")
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        movieViewModel.getTrendingMoviesList().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "trendingMoviesList observe")
            if (it != null) {
                trendingAdapter.submitData(lifecycle, it)
            }
        })
        movieViewModel.getTrendingMoviesWithPage()
    }

    private fun initRV() {
        Log.d(TAG, "initRV")
        binding.trendingRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = trendingAdapter
            trendingAdapter.setTrendingMovieClickedListener(this@TrendingFragment)
        }
    }

    override fun onTrendingMovieClicked(movie: TmdbMovieModel) {
        Log.d(TAG, "onTrendingMovieClicked")
        movieViewModel.getExternalIdFromTmdb(movie.getTmdbId())
        movieViewModel.getExternalIdLiveData().observe(viewLifecycleOwner, Observer {
            if(it != null) {
                Log.d(TAG, "clicked; not null")
                val action = TrendingFragmentDirections.actionTrendingFragmentToFullMovieDialog(it)
                findNavController().navigate(action)
            }
        })
    }

    private fun showShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLongSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (!isOnline(requireContext())) {
            showLongSnackbar("No internet connection")
        }
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

}