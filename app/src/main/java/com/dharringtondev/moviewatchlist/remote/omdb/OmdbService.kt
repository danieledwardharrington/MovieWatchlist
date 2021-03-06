package com.dharringtondev.moviewatchlist.remote.omdb

import com.dharringtondev.moviewatchlist.BuildConfig
import com.dharringtondev.moviewatchlist.remote.omdb.models.MovieModel
import com.dharringtondev.moviewatchlist.remote.omdb.models.OmdbResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("?apikey=1fa7ed15")
    fun getRemoteMovies(@Query("s") title: String): Observable<OmdbResponse>

    @GET("?apikey=${BuildConfig.OMDB_API_KEY}&type=movie")
    fun getRemoteMoviesWithPage(@Query("s") title: String, @Query("page") page: String): Single<OmdbResponse>


    /*
    The OMDb API only returns a limited amount of information when searching for movies, so this function is
    here to take the IMDb ID number from the returned search and then use it to get a more detailed object for
    the dialog, which displays detailed information about the movie
     */
    @GET("?apikey=1fa7ed15")
    fun getMovieById(@Query("i") imdbId: String): Observable<MovieModel>

    companion object {
        fun create(): OmdbService {
            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(BuildConfig.BASE_OMDB_URL)
                    .build()

            return retrofit.create(OmdbService::class.java)
        }
    }
}