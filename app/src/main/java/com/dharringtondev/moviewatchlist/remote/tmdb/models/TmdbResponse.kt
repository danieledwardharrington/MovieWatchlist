package com.dharringtondev.moviewatchlist.remote.tmdb.models

import com.dharringtondev.moviewatchlist.remote.tmdb.models.TmdbMovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse(@SerializedName("results")
                   @Expose
                   val results: MutableList<TmdbMovieModel>,
                   @SerializedName("total_results")
                   @Expose
                   val totalResults: Int
)