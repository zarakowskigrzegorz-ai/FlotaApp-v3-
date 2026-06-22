package com.aistudio.fleetmanager.mvp.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnalyticsDao {

    // 1. Wyciąga sumę kosztów dla konkretnego ciągnika w danym czasie
    @Query("""
        SELECT SUM(amountNetto) 
        FROM costs 
        WHERE fkTruckId = :truckId 
          AND transactionDate BETWEEN :startDate AND :endDate
    """)
    fun getTotalVariableCosts(truckId: String, startDate: Long, endDate: Long): Double

    // 2. Główny raport rentowności (Zysk = Przychód - Koszty Zmienne dla konkretnej trasy)
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

// Struktura danych, która odbiera wynik z powyższego zapytania SQL
data class TripProfitReport(
    val tripId: Long,
    val ladunek: String,
    val przychodPln: Double,
    val przejechaneKm: Int,
    val kosztyZmiennePln: Double,
    val czystyZyskPln: Double
)
