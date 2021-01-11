package com.dharringtondev.moviewatchlist.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.dharringtondev.moviewatchlist.persistence.MovieDao
import com.dharringtondev.moviewatchlist.persistence.MovieDatabase
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
import com.dharringtondev.moviewatchlist.remote.omdb.models.MovieModel
import com.dharringtondev.moviewatchlist.remote.omdb.OmdbService
import com.dharringtondev.moviewatchlist.remote.tmdb.TmdbService
import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbMovieModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieRepository(application: Application) {
    private val TAG = "MovieRepository"

    private val database = MovieDatabase.getWatchlistInstance(application)
    private val movieDao: MovieDao = database!!.watchlistDao()
    private var compositeDisposable = CompositeDisposable()

    private var allMoviesLiveData = MutableLiveData<List<MovieEntity>>()
    private var watchedMoviesLiveData = MutableLiveData<List<MovieEntity>>()
    private var movieWatchlistLiveData = MutableLiveData<List<MovieEntity>>()
    private var remoteMoviesLiveData = MutableLiveData<PagingData<MovieModel>>()
    private var trendingMoviesLiveData = MutableLiveData<PagingData<TmdbMovieModel>>()
    private var movieByIdLiveData = MutableLiveData<MovieModel>()
    private var externalIdLiveData = MutableLiveData<String>()

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

    /*
    All movies are saved to the table with a "watched" field. We get all movies in the fragment and then narrow it to watched or unwatched (watchlist)
     */
    fun getAllWatchedMovies() {
        movieDao.getAllWatchedMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                if (!it.isNullOrEmpty()) {
                    watchedMoviesLiveData.postValue(it)
                    allMoviesLiveData.postValue(it)
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
                    allMoviesLiveData.postValue(it)
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

    fun getAllMoviesLiveData(): MutableLiveData<List<MovieEntity>> {
        return allMoviesLiveData
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
        ).let { compositeDisposable.add(it) }
    }

    fun getRemoteMovieByIdLiveData(): MutableLiveData<MovieModel> {
        return movieByIdLiveData
    }

    fun getRemoteMoviesLiveData(): MutableLiveData<PagingData<MovieModel>> {
        return remoteMoviesLiveData
    }

    fun getTrendingMoviesLiveData(): MutableLiveData<PagingData<TmdbMovieModel>> {
        return trendingMoviesLiveData
    }

    fun getRemoteMoviesWithPage(filter: String): Flowable<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {RemoteOmdbMoviePagingSource(OmdbService.create(), filter)}
        ).flowable
    }

    //a function to cast the MovieModel, object returned from OMDb, to MovieEntity, object stored in the database
    fun modelToEntity(movieModel: MovieModel): MovieEntity {
        return MovieEntity(movieModel.getImdbId(), movieModel.getTitle(), movieModel.getYear(), movieModel.getDirector(), movieModel.getWriter(), movieModel.getActors(), movieModel.getAwards(),
                movieModel.getPlot(), movieModel.getLanguage(), movieModel.getCountry(), movieModel.getImdbRating(), movieModel.getPosterUrl(), movieModel.getAgeRating(), movieModel.getRuntime(), movieModel.getGenre())
    }

    fun getTrendingMoviesWithPage(): Flowable<PagingData<TmdbMovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {RemoteTmdbMoviePagingSource(TmdbService.create())}
        ).flowable
    }

    fun getExternalIdFromTmdb(tmdbId: Int) {
        TmdbService.create().getTmdbMovieExternalIds(tmdbId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                externalIdLiveData.postValue(it.getImdbId())
            },
            {
                Log.e(TAG, it.toString())
            }).let { compositeDisposable.add(it) }
    }

    fun getExternalIdLiveData(): MutableLiveData<String> {
        return externalIdLiveData
    }

}