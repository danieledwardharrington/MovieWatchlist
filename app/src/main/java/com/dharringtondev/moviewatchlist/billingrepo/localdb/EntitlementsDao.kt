package com.dharringtondev.moviewatchlist.billingrepo.localdb

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * No update methods necessary since for each table there is ever expecting one
 * row, hence why the primary key is hardcoded.
 */
@Dao
interface EntitlementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goldStatus: CachedPurchaseDatabase.GoldStatus)

    @Update
    fun update(goldStatus: CachedPurchaseDatabase.GoldStatus)

    @Query("SELECT * FROM gold_status LIMIT 1")
    fun getGoldStatus(): LiveData<CachedPurchaseDatabase.GoldStatus>

    @Delete
    fun delete(goldStatus: CachedPurchaseDatabase.GoldStatus)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(premium: CachedPurchaseDatabase.PremiumCar)

    @Update  fun update(premium: CachedPurchaseDatabase.PremiumCar)

    @Query("SELECT * FROM premium_car LIMIT 1")
    fun getPremiumCar(): LiveData<CachedPurchaseDatabase.PremiumCar>

    @Delete
    fun delete(premium: CachedPurchaseDatabase.PremiumCar)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gasLevel: CachedPurchaseDatabase.GasTank)

    @Update
    fun update(gasLevel: CachedPurchaseDatabase.GasTank)

    @Query("SELECT * FROM gas_tank LIMIT 1")
    fun getGasTank(): LiveData<CachedPurchaseDatabase.GasTank>

    @Delete
    fun delete(gasLevel: CachedPurchaseDatabase.GasTank)

    /**
     * This is purely for convenience. The clients of this DAO don't have to
     * discriminate among [GasTank] vs [PremiumCar] vs [GoldStatus] but can
     * simply send in a list of [entitlements][Entitlement].
     */
    @Transaction
    fun insert(vararg entitlements: Entitlement) {
        entitlements.forEach {
            when (it) {
                is GasTank -> insert(it)
                is CachedPurchaseDatabase.PremiumCar -> insert(it)
                is CachedPurchaseDatabase.GoldStatus -> insert(it)
            }
        }
    }

    @Transaction
    fun update(vararg entitlements: Entitlement) {
        entitlements.forEach {
            when (it) {
                is CachedPurchaseDatabase.GasTank -> update(it)
                is CachedPurchaseDatabase.PremiumCar -> update(it)
                is CachedPurchaseDatabase.GoldStatus -> update(it)
            }
        }
    }
}