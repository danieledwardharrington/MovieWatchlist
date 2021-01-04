package com.dharringtondev.moviewatchlist.repository

import androidx.paging.rxjava3.RxPagingSource
import com.dharringtondev.moviewatchlist.remote.MovieModel
import com.dharringtondev.moviewatchlist.remote.OmdbResponse
import com.dharringtondev.moviewatchlist.remote.OmdbService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val REMOTE_STARTING_PAGE_INDEX = 1

class RemoteMoviePagingSource(private val omdbService: OmdbService, private val query: String): RxPagingSource<Int, MovieModel>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieModel>> {
        val position = params.key ?: REMOTE_STARTING_PAGE_INDEX

        return omdbService.getRemoteMoviesWithPage(query, position.toString()).subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (position == REMOTE_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (it.results.isEmpty()) null else position + 1
                ) as LoadResult<Int, MovieModel>
            }.onErrorReturn {
                LoadResult.Error(it)
            }
    }
}