package com.dharringtondev.moviewatchlist.persistence

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity): Completable

    @Delete
    fun delete(movie: MovieEntity): Completable

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie_table ORDER BY id ASC")
    fun getAllMovies(): Observable<List<MovieEntity>>

}