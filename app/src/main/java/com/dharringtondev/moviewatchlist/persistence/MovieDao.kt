package com.dharringtondev.moviewatchlist.persistence

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity): Completable

    @Delete
    fun delete(movie: MovieEntity): Completable

    @Update
    fun update(movie: MovieEntity): Completable

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies(): Completable

    @Query("SELECT * FROM movie_table ORDER BY id ASC")
    fun getAllMovies(): Observable<List<MovieEntity>>

    @Query("SELECT * FROM movie_table WHERE watched=1")
    fun getAllWatchedMovies(): Observable<List<MovieEntity>>

    @Query("SELECT * FROM movie_table WHERE watched=0")
    fun getMovieWatchlist(): Observable<List<MovieEntity>>

}