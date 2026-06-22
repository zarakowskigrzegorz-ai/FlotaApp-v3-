package com.aistudio.fleetmanager.mvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cargoName: String,            
    val startLocation: String,        
    val endLocation: String,          
    val priceEur: Double,             
    val fkTruckId: String,            
    val fkDriverId: String,           
    val startOdometer: Int,           
    val endOdometer: Int? = null,     
    val startDate: Long,              
    val endDate: Long? = null         
)
