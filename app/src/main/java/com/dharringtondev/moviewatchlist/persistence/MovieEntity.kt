package com.dharringtondev.moviewatchlist.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(@ColumnInfo(name = "imdb_id")
                       private var imdbId: String = "",
                       @ColumnInfo(name = "title")
                       private var title: String = "",
                       @ColumnInfo(name = "year")
                       private var year: String = "",
                       @ColumnInfo(name = "director")
                       private var director: String = "",
                       @ColumnInfo(name = "writer")
                       private var writer: String = "",
                       @ColumnInfo(name = "actors")
                       private var actors: String = "",
                       @ColumnInfo(name = "awards")
                       private var awards: String = "",
                       @ColumnInfo(name = "plot")
                       private var plot: String = "",
                       @ColumnInfo(name = "language")
                       private var language: String = "",
                       @ColumnInfo(name = "country")
                       private var country: String = "",
                       @ColumnInfo(name = "imdb_rating")
                       private var imdbRating: String = "",
                       @ColumnInfo(name = "poster_url")
                       private var posterUrl: String = "",
                       @ColumnInfo(name = "age_rating")
                       private var ageRating: String = "",
                       @ColumnInfo(name = "runtime")
                       private var runtime: String = "",
                       @ColumnInfo(name = "genre")
                       private var genre: String = ""
                       ) {

    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0

    @ColumnInfo(name = "watched")
    private var watched: Boolean = false

    fun getId(): Long {
        return id
    }

    fun setId(newId: Long) {
        id = newId
    }

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

    fun getWatched(): Boolean {
        return watched
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

    fun setWatched(hasWatched: Boolean) {
        watched = hasWatched
    }
}