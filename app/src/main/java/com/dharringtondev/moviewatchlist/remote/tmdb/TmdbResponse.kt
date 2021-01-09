package com.dharringtondev.moviewatchlist.remote.tmdb

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse(@SerializedName("resullts")
                   @Expose
                   val results: MutableList<TmdbMovieModel>,
                   @SerializedName("total_results")
                   @Expose
                   val totalResults: Int
)