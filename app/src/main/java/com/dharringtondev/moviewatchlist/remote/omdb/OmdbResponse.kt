package com.dharringtondev.moviewatchlist.remote.omdb

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OmdbResponse(@SerializedName("Search")
                        @Expose
                        val results: MutableList<MovieModel>,
                        @SerializedName("totalResults")
                        @Expose
                        val totalResults: String)