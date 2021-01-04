package com.dharringtondev.moviewatchlist.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("Title")
    @Expose
    private var title: String = "",

    @SerializedName("Year")
    @Expose
    private var year: String = "",

    @SerializedName("Director")
    @Expose
    private var director: String = "",

    @SerializedName("Writer")
    private var writer: String = "",

    @SerializedName("Actors")
    @Expose
    private var actors: String = "",

    @SerializedName("Awards")
    @Expose
    private var awards: String = "",

    @SerializedName("Plot")
    @Expose
    private var plot: String = "",

    @SerializedName("Language")
    @Expose
    private var language: String = "",

    @SerializedName("Country")
    @Expose
    private var country: String = "",

    @SerializedName("imdbRating")
    @Expose
    private var imdbRating: String = "",

    @SerializedName("imdbID")
    @Expose
    private var imdbId: String = "",

    @SerializedName("Poster")
    @Expose
    private var posterUrl: String = "",

    @SerializedName("Rated")
    @Expose
    private var ageRating: String = "",

    @SerializedName("Runtime")
    @Expose
    private var runtime: String = "",

    @SerializedName("Genre")
    @Expose
    private var genre: String = "") {

    fun getImdbId(): String {
        return imdbId
    }

    fun setImdbId(newId: String) {
        imdbId = newId
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(newTitle: String) {
        title = newTitle
    }

    fun getYear(): String {
        return year
    }

    fun setYear(newYear: String) {
        year = newYear
    }

    fun getDirector(): String {
        return director
    }

    fun setDirector(newDirector: String) {
        director = newDirector
    }

    fun getWriter(): String {
        return writer
    }

    fun setWriter(newWriter: String) {
        writer = newWriter
    }

    fun getActors(): String {
        return actors
    }

    fun setActors(newActors: String) {
        actors = newActors
    }

    fun getAwards(): String {
        return awards
    }

    fun setAwards(newAwards: String) {
        awards = newAwards
    }

    fun getPlot(): String {
        return plot
    }

    fun setPlot(newPlot: String) {
        plot = newPlot
    }

    fun getLanguage(): String {
        return language
    }

    fun setLanguage(newLanguage: String) {
        language = newLanguage
    }

    fun getCountry(): String {
        return country
    }

    fun setCountry(newCountry: String) {
        country = newCountry
    }

    fun getImdbRating(): String {
        return imdbRating
    }

    fun setImdbRating(newRating: String) {
        imdbRating = newRating
    }

    fun getPosterUrl(): String {
        return posterUrl
    }

    fun setPosterUrl(newUrl: String) {
        posterUrl = newUrl
    }

    fun getAgeRating(): String {
        return ageRating
    }

    fun setAgeRating(newRating: String) {
        ageRating = newRating
    }

    fun setRuntime(newRuntime: String) {
        runtime = newRuntime
    }

    fun getRuntime(): String {
        return runtime
    }

    fun setGenre(newGenre: String) {
        genre = newGenre
    }

    fun getGenre(): String {
        return genre
    }

}