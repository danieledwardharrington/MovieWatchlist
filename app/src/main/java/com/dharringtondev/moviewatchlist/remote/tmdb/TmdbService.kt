package com.dharringtondev.moviewatchlist.remote.tmdb

import com.dharringtondev.moviewatchlist.BuildConfig
import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbIdModel
import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbService {

    @GET("3/trending/movie/week?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTrendingMoviesWeek(): Single<TmdbResponse>

    @GET("3/trending/movie/day?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTrendingMoviesDay(): Single<TmdbResponse>

    @GET("3/movie/{tmdbId}/external_ids?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTmdbMovieExternalIds(@Path("tmdbId") tmdbId: String): Observable<TmdbIdModel>

    companion object {
        fun create(): TmdbService {
            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.BASE_TMDB_URL)
                .build()

            return retrofit.create(TmdbService::class.java)
        }
    }
}