package com.dharringtondev.moviewatchlist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.persistence.MovieRepository
import com.dharringtondev.moviewatchlist.remote.MovieModel

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

    fun getRemoteMovies(filter: String) {
        movieRepository.getRemoteMovies(filter)
    }

    fun deleteAllMovies() {
        movieRepository.deleteAllMovies()
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

    fun getRemoteMoviesList(): MutableLiveData<List<MovieModel>> {
        return movieRepository.getRemoteMoviesLiveData()
    }

    fun getRemoteMovieById(imdbId: String) {
        movieRepository.getRemoteMovieById(imdbId)
    }

    fun getRemoteMovieByIdLiveData(): MutableLiveData<MovieModel> {
        return movieRepository.getRemoteMovieByIdLiveData()
    }

    fun modelToEntity(movieModel: MovieModel): MovieEntity {
        return movieRepository.modelToEntity(movieModel)
    }

}