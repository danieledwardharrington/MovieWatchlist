package com.dharringtondev.moviewatchlist.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OmdbResponse(@SerializedName("Search")
                        @Expose
                        val results: List<MovieModel>)