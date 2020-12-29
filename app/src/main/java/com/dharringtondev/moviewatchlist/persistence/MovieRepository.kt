package com.dharringtondev.moviewatchlist.persistence

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dharringtondev.moviewatchlist.remote.MovieModel
import com.dharringtondev.moviewatchlist.remote.OmdbService
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
    private var remoteMoviesLiveData = MutableLiveData<List<MovieModel>>()
    private var movieByIdLiveData = MutableLiveData<MovieModel>()

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

    fun getRemoteMovies(filter: String) {
        Log.d(TAG, "getRemoteMovies")
        OmdbService.create().getRemoteMovies(filter, "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                if(it != null) {
                    remoteMoviesLiveData.postValue(it.results)
                }
            },
            {
                Log.e(TAG, it.toString())
            }
        ).let {
            compositeDisposable.add(it)
        }
    }

    fun getRemoteMovieById(imdbId: String) {
        Log.d(TAG, "getMovieById: $imdbId")
        OmdbService.create().getMovieById(imdbId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                if(it != null) {
                    Log.d(TAG, "movieById not null")
                    movieByIdLiveData.postValue(it)
                }
            },
            {
                Log.e(TAG, it.toString())
            }
        )
    }

    fun getRemoteMovieByIdLiveData(): MutableLiveData<MovieModel> {
        return movieByIdLiveData
    }

    fun getRemoteMoviesLiveData(): MutableLiveData<List<MovieModel>> {
        return remoteMoviesLiveData
    }

    fun modelToEntity(movieModel: MovieModel): MovieEntity {
        return MovieEntity(movieModel.getImdbId(), movieModel.getTitle(), movieModel.getYear(), movieModel.getDirector(), movieModel.getWriter(), movieModel.getActors(), movieModel.getAwards(),
                movieModel.getPlot(), movieModel.getLanguage(), movieModel.getCountry(), movieModel.getImdbRating(), movieModel.getPosterUrl(), movieModel.getAgeRating(), movieModel.getRuntime(), movieModel.getGenre())
    }

}