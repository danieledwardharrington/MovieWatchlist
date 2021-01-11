package com.dharringtondev.moviewatchlist.repository

import android.util.Log
import androidx.paging.rxjava3.RxPagingSource
import com.dharringtondev.moviewatchlist.remote.tmdb.TmdbService
import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbMovieModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val REMOTE_STARTING_PAGE_INDEX = 1

class RemoteTmdbMoviePagingSource(private val tmdbService: TmdbService): RxPagingSource<Int, TmdbMovieModel>() {

    private val TAG = "RemoteTmdbMoviePagingSource"

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TmdbMovieModel>> {
        Log.d(TAG, "loadSingle")
        val position = params.key ?: REMOTE_STARTING_PAGE_INDEX

        return tmdbService.getTrendingMoviesWeek().subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (position == REMOTE_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (it.results.isEmpty()) null else position + 1
                ) as LoadResult<Int, TmdbMovieModel>
            }.onErrorReturn {
                LoadResult.Error(it)
            }
    }
}