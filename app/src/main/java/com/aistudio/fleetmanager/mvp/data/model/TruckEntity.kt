package com.aistudio.fleetmanager.mvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trucks")
data class TruckEntity(
    @PrimaryKey val truckId: String,       // Numer rejestracyjny ciągnika (np. PO12345)
    val trailerLicensePlate: String,        // Numer rejestracyjny naczepy
    val brand: String,                      // Marka (np. Scania, DAF)
    val model: String,                      // Model
    val fuelNormPer100km: Double,          // Norma spalania (potrzebna do alertów o przepałach)
    val fixedCostsMonthly: Double          // Koszty stałe: leasing + ubezpieczenie na miesiąc
)
