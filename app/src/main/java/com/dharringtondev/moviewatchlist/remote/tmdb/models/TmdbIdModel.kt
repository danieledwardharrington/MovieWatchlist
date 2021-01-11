package com.dharringtondev.moviewatchlist.remote.tmdb.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbIdModel {
    @SerializedName("imdb_id")
    @Expose
    private var imdbId: String = ""

    fun getImdbId(): String {
        return imdbId
    }

    fun setImdbId(newId: String) {
        imdbId = newId
    }
}