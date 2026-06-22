package com.aistudio.fleetmanager.mvp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aistudio.fleetmanager.mvp.data.model.*

@Dao
interface AnalyticsDao {

    // --- NOWE FUNKCJE DO ZAPISU DANYCH ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTruck(truck: TruckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriver(driver: DriverEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCost(cost: CostEntity)

    // --- TWOJE WCZEŚNIEJSZE FUNKCJE DO ANALITYKI ---
    @Query("""
        SELECT SUM(amountNetto) 
        FROM costs 
        WHERE fkTruckId = :truckId 
          AND transactionDate BETWEEN :startDate AND :endDate
    """)
    fun getTotalVariableCosts(truckId: String, startDate: Long, endDate: Long): Double

    @Query("""
        SELECT 
            t.id AS tripId,
            t.cargoName AS ladunek,
            t.priceEur * 4.30 AS przychodPln,
            (t.endOdometer - t.startOdometer) AS przejechaneKm,
            (
                SELECT COALESCE(SUM(c.amountNetto), 0.0) 
                FROM costs c 
                WHERE c.fkTruckId = t.fkTruckId 
                  AND c.transactionDate BETWEEN t.startDate AND t.endDate
            ) AS kosztyZmiennePln,
            (
                t.priceEur * 4.30 - (
                    SELECT COALESCE(SUM(c.amountNetto), 0.0) 
                    FROM costs c 
                    WHERE c.fkTruckId = t.fkTruckId 
                      AND c.transactionDate BETWEEN t.startDate AND t.endDate
                )
            ) AS czystyZyskPln
        FROM trips t
        WHERE t.endOdometer IS NOT NULL AND t.endDate IS NOT NULL
    """)
    fun getTripProfitabilityReport(): List<TripProfitReport>
}

data class TripProfitReport(
    val tripId: Long,
    val ladunek: String,
    val przychodPln: Double,
    val przejechaneKm: Int,
    val kosztyZmiennePln: Double,
    val czystyZyskPln: Double
)
