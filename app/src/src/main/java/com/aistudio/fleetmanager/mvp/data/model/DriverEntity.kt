package com.aistudio.fleetmanager.mvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drivers")
data class DriverEntity(
    @PrimaryKey val driverId: String,       // ID pracownika lub PESEL
    val firstName: String,
    val lastName: String,
    val baseSalary: Double,                 // Podstawa pensji
    val ratePerKm: Double                   // Ewentualna premia od kilometra
)
