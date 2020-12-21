package com.dharringtondev.moviewatchlist.persistence

import androidx.room.*
import com.dharringtondev.moviewatchlist.persistence.MovieEntity
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

    @Query("SELECT * FROM movie_table WHERE watched=1")
    fun getAllWatchedMovies(): Observable<List<MovieEntity>>

    @Query("SELECT * FROM movie_table WHERE watched=0")
    fun getMovieWatchlist(): Observable<List<MovieEntity>>

}