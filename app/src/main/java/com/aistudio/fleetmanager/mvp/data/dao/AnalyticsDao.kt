package com.aistudio.fleetmanager.mvp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aistudio.fleetmanager.mvp.data.model.*

@Dao
interface AnalyticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTruck(truck: TruckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriver(driver: DriverEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCost(cost: CostEntity)

    @Query("""
        SELECT SUM(amountNetto) 
        FROM costs 
        WHERE fkTruckId = :truckId 
          AND transactionDate BETWEEN :startDate AND :endDate
    """)
    fun getTotalVariableCosts(truckId: String, startDate: Long, endDate: Long): Double?

    @Query("""
        SELECT 
            t.id AS tripId,
            t.cargoName AS ladunek,
            (t.priceEur * 4.30) AS przychodPln,
            (COALESCE(t.endOdometer, t.startOdometer) - t.startOdometer) AS przejechaneKm,
            COALESCE((
                SELECT SUM(c.amountNetto) 
                FROM costs c 
                WHERE c.fkTruckId = t.fkTruckId 
                  AND c.transactionDate BETWEEN t.startDate AND t.endDate
            ), 0.0) AS kosztyZmiennePln,
            ((t.priceEur * 4.30) - COALESCE((
                SELECT SUM(c.amountNetto) 
                FROM costs c 
                WHERE c.fkTruckId = t.fkTruckId 
                  AND c.transactionDate BETWEEN t.startDate AND t.endDate
            ), 0.0)) AS czyskZyskPln
        FROM trips t
    """)
    fun getTripProfitabilityReport(): List<TripProfitReport>
}

data class TripProfitReport(
    val tripId: Long,
    val ladunek: String,
    val przychodPln: Double?,
    val przejechaneKm: Int?,
    val kosztyZmiennePln: Double?,
    val czyskZyskPln: Double?
)
