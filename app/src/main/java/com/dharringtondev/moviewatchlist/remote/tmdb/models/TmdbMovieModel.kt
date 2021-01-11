package com.dharringtondev.moviewatchlist.remote.tmdb.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TmdbMovieModel(@SerializedName("original_title")
                          @Expose
                          private var title: String = "",

                          @SerializedName("release_date")
                          @Expose
                          private var releaseDate: String = "",

                          @SerializedName("poster_path")
                          @Expose
                          private var posterPath: String = "",

                          @SerializedName("id")
                          @Expose
                          private var tmdbId: Int = 0) {

    //The release date includes day and month, I only need the year so I'll take care of that with this
    private var releaseYear = ""
    private val basePosterPath = "https://image.tmdb.org/t/p/original/"

    fun getTitle(): String {
        return title
    }

    fun setTitle(newTitle: String) {
        title = newTitle
    }

    fun getReleaseDate(): String {
        return releaseDate
    }

    fun setReleaseDate(newDate: String) {
        releaseDate = newDate
    }

    fun getPosterPath(): String {
        return basePosterPath.plus(posterPath)
    }

    fun setPosterPath(newPath: String) {
        posterPath = newPath
    }

    fun getReleaseYear(): String {
        if (releaseDate.isNotEmpty()) {
            releaseYear = releaseDate.take(4) //grabbing first four digits of date, which is the year for tmdb
        }
        return releaseYear
    }

    fun getTmdbId(): Int {
        return tmdbId
    }

    fun setTmdbId(newId: Int) {
        tmdbId = newId
    }

}