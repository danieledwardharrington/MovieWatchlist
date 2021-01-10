package com.dharringtondev.moviewatchlist.remote.tmdb

import android.content.res.Resources
import com.dharringtondev.moviewatchlist.BuildConfig
import com.dharringtondev.moviewatchlist.R
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("3/trending/movie/week?api+key=${BuildConfig.TMDB_API_KEY}")
    fun getTrendingMoviesWeek(): Single<TmdbResponse>

    @GET("3/trending/movie/day?api+key=${BuildConfig.TMDB_API_KEY}")
    fun getTrendingMoviesDay(): Single<TmdbResponse>

    @GET("3/movie/$tmdbId")
    fun getTmdbImdbId(tmdbId: Int)

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