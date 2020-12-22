package com.dharringtondev.moviewatchlist.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(@ColumnInfo(name = "imdb_id")
                       private var imdbID: String = "",
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
                       @ColumnInfo(name = "watched")
                       private var watched: Boolean = false
                       ) {

    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0
}