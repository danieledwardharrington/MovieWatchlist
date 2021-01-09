package com.dharringtondev.moviewatchlist.remote.tmdb

import com.dharringtondev.moviewatchlist.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface TmdbService {

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