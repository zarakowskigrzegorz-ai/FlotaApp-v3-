package com.aistudio.fleetmanager.mvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "costs")
data class CostEntity(
    @PrimaryKey(autoGenerate = true) val costId: Long = 0,
    val fkTruckId: String,            // Pod którą ciężarówkę podpiąć koszt
    val transactionDate: Long,        // Data transakcji (z pliku CSV)
    val costType: String,             // "FUEL", "ADBLUE", "TOLL", "SERVICE"
    val amountNetto: Double,          // Kwota netto
    val amountBrutto: Double,         // Kwota brutto
    val volumeLiters: Double? = null, // Ilość litrów (dla paliwa)
    val locationOrProvider: String    // np. "Stacja AS24 Berlin"
)
