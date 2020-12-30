package com.dharringtondev.moviewatchlist.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun watchlistDao(): MovieDao

    companion object {

        private var watchlistInstance: MovieDatabase? = null

        fun getWatchlistInstance(context: Context): MovieDatabase? {
            if (watchlistInstance == null) {
                synchronized(MovieDatabase::class) {
                    watchlistInstance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "watchlist_db.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return watchlistInstance
        }

    }

}