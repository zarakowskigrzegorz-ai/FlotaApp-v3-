package com.aistudio.fleetmanager.mvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "costs")
data class CostEntity(
    @PrimaryKey(autoGenerate = true) val costId: Long = 0,
    val fkTruckId: String,            
    val transactionDate: Long,        
    val costType: String,             
    val amountNetto: Double,          
    val amountBrutto: Double,         
    val volumeLiters: Double? = null, 
    val locationOrProvider: String    
)
