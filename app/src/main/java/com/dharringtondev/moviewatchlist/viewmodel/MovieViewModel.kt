package com.dharringtondev.moviewatchlist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.repository.MovieRepository
import com.dharringtondev.moviewatchlist.remote.omdb.MovieModel

class MovieViewModel(application: Application): ViewModel() {
    private val TAG = "MovieViewModel"

    private val movieRepository = MovieRepository(application)

    /*
    This list is to track the movies that the user has swiped away from the
    search screen.
     */
    private val swipedMovieIds = ArrayList<String>()

    val tutorialSeenLD = MutableLiveData<Boolean>()

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

/*    fun getRemoteMovies(filter: String) {
        movieRepository.getRemoteMovies(filter)
    }*/

    fun deleteAllMovies() {
        movieRepository.deleteAllMovies()
    }

    override fun onCleared() {
        movieRepository.getCompositeDisposable().clear()
    }

    fun getWatchlist(): MutableLiveData<List<MovieEntity>> {
        return movieRepository.getMovieWatchlistLiveData()
    }

    fun getWatchedMoviesList(): MutableLiveData<List<MovieEntity>> {
        return movieRepository.getWatchedMoviesLiveData()
    }

    fun getAllMoviesList(): MutableLiveData<List<MovieEntity>> {
        return movieRepository.getAllMoviesLiveData()
    }

    fun getRemoteMoviesList(): MutableLiveData<PagingData<MovieModel>> {
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

    fun getRemoteMoviesWithPage(filter: String) {
        movieRepository.getCompositeDisposable().add(
            movieRepository.getRemoteMoviesWithPage(filter)
                .cachedIn(viewModelScope)
                .subscribe{
                    movieRepository.getRemoteMoviesLiveData().value = it
                }
        )
    }

    fun getSwipedMovieIds(): ArrayList<String> {
        return swipedMovieIds
    }

}