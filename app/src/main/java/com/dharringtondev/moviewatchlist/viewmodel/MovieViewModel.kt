package com.dharringtondev.moviewatchlist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.persistence.MovieRepository

class MovieViewModel(application: Application): ViewModel() {

    private val movieRepository = MovieRepository(application)

    fun insert(movieEntity: MovieEntity) {
        movieRepository.insert(movieEntity)
    }

    fun update(movieEntity: MovieEntity) {
        movieRepository.update(movieEntity)
    }

    fun delete(movieEntity: MovieEntity) {
        movieRepository.delete(movieEntity)
    }

    fun getAllMovies() {
        getMovieWatchlist()
        getWatchedMovies()
    }

    private fun getMovieWatchlist() {
        movieRepository.getMovieWatchlist()
    }

    private fun getWatchedMovies() {
        movieRepository.getAllWatchedMovies()
    }

    override fun onCleared() {
        movieRepository.getCompositeDisposable().clear()
    }

    fun getWatchlist(): MutableLiveData<List<MovieEntity>> {
        return movieRepository.getMovieWatchlistLiveData()
    }

    fun getWatchedMoviesList(): MutableLiveData<List<MovieEntity>>{
        return movieRepository.getWatchedMoviesLiveData()
    }

}