package com.dharringtondev.moviewatchlist.remote

import com.dharringtondev.moviewatchlist.BuildConfig
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("?apikey=1fa7ed15")
    fun getRemoteMovies(@Query("t") title: String): Observable<MovieModel>

    companion object {
        fun create(): OmdbService {
            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()

            return retrofit.create(OmdbService::class.java)
        }
    }
}