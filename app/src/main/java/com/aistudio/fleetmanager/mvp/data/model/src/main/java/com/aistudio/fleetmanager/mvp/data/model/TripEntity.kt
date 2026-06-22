package com.aistudio.fleetmanager.mvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cargoName: String,            // Co wieziemy
    val startLocation: String,        // Miejsce załadunku
    val endLocation: String,          // Miejsce rozładunku
    val priceEur: Double,             // Przychód za fracht
    val fkTruckId: String,            // Którym ciągnikiem (powiązanie z rejestracją)
    val fkDriverId: String,           // Który kierowca
    val startOdometer: Int,           // Stan licznika start
    val endOdometer: Int? = null,     // Stan licznika koniec (wypełniane na koniec)
    val startDate: Long,              // Data wyjazdu
    val endDate: Long? = null         // Data powrotu
)
