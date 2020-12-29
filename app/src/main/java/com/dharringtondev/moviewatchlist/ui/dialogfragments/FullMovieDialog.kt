package com.dharringtondev.moviewatchlist.ui.dialogfragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.AlertDialogFullMovieBinding
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.remote.MovieModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory

class FullMovieDialog: DialogFragment() {
    private val TAG = "FullMovieDialog"

    private val fmdArgs: FullMovieDialogArgs by navArgs()
    private lateinit var movieViewModel: MovieViewModel
    private var imdbId: String = ""

    private var _binding: AlertDialogFullMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        _binding = AlertDialogFullMovieBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root).setPositiveButton("Okay") { _, _ -> dismiss() }
        initComponents()
        return builder.create()
    }

    private fun initComponents() {
        Log.d(TAG, "onViewCreated")
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
        imdbId = fmdArgs.imdbId
        Log.d(TAG, "initComponents; imdbId: $imdbId")
        movieViewModel.getRemoteMovieById(imdbId)
        movieViewModel.getRemoteMovieByIdLiveData().observe(this, Observer {
            Log.d(TAG, "observe; ${it.getTitle()}")
            setupText(it)
        })
    }

    private fun setupText(movie: MovieModel) {
        Log.d(TAG, "setupText")
        binding.apply {
            movieTitleTv.text = movie.getTitle()
            yearTv.text = movie.getYear()
            runtimeTv.text = movie.getRuntime()
            imdbRatingTv.text = movie.getImdbRating()
            plotTv.text = movie.getPlot()
            genreTv.text = movie.getGenre()
            directorTv.text = movie.getDirector()
            writerTv.text = movie.getWriter()
            starringTv.text = movie.getActors()
            awardsTv.text = movie.getAwards()
            countryTv.text = movie.getCountry()
            languageTv.text = movie.getLanguage()
            Glide.with(requireContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_poster_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(posterIv)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}