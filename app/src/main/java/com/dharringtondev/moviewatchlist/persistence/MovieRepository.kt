package com.dharringtondev.moviewatchlist.persistence

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieRepository(application: Application) {
    private val TAG = "MovieRepository"

    private val database = MovieDatabase.getWatchlistInstance(application)
    private val movieDao: MovieDao = database!!.watchlistDao()
    private var compositeDisposable = CompositeDisposable()

    private var watchedMoviesLiveData = MutableLiveData<List<MovieEntity>>()
    private var movieWatchlistLiveData = MutableLiveData<List<MovieEntity>>()

    fun insert(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {getAllWatchedMovies()},
            {Log.d(TAG, it.toString())}
        ).let {
            compositeDisposable.add(it)
        }
    }

    fun delete(movieEntity: MovieEntity) {
        movieDao.delete(movieEntity).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {getAllWatchedMovies()},
            {Log.d(TAG, it.toString())}
        ).let {
            compositeDisposable.add(it)
        }
    }

    fun update(movieEntity: MovieEntity) {
        movieDao.update(movieEntity).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
        {Log.d(TAG, "Movie updated")},
        {Log.d(TAG, it.toString())}
        ).let { compositeDisposable.add(it) }
    }

    fun getAllWatchedMovies() {
        movieDao.getAllWatchedMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                if (!it.isNullOrEmpty()) {
                    watchedMoviesLiveData.postValue(it)
                }
            },
            {Log.d(TAG, it.toString())}
        ).let {
            compositeDisposable.add(it)
        }
    }

    fun getMovieWatchlist() {
        movieDao.getMovieWatchlist().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                if (!it.isNullOrEmpty()) {
                    movieWatchlistLiveData.postValue(it)
                }
            },
            {Log.d(TAG, it.toString())}
        ).let {
            compositeDisposable.add(it)
        }
    }

    fun deleteAllMovies() {
        movieDao.deleteAllMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {Log.d(TAG, "All movies deleted")},
            {Log.d(TAG, it.toString())}
        ).let { compositeDisposable.add(it) }
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return compositeDisposable
    }

    fun getMovieWatchlistLiveData(): MutableLiveData<List<MovieEntity>> {
        return movieWatchlistLiveData
    }

    fun getWatchedMoviesLiveData(): MutableLiveData<List<MovieEntity>> {
        return watchedMoviesLiveData
    }

}