package com.aistudio.fleetmanager.mvp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aistudio.fleetmanager.mvp.data.model.TruckEntity
import com.aistudio.fleetmanager.mvp.data.model.DriverEntity
import com.aistudio.fleetmanager.mvp.data.model.TripEntity
import com.aistudio.fleetmanager.mvp.data.model.CostEntity
import com.aistudio.fleetmanager.mvp.data.dao.AnalyticsDao

// Wymieniamy wszystkie tabele (Entities) w naszej bazie
@Database(
    entities = [
        TruckEntity::class,
        DriverEntity::class,
        TripEntity::class,
        CostEntity::class
    ],
    version = 1, // Ważne: przy zmianach w bazie (np. dodaniu kolumny), będziemy to zwiększać
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    // Podpinamy nasz moduł analityczny
    abstract fun analyticsDao(): AnalyticsDao
    
    // W przyszłości dodamy tu inne DAO, np. do zarządzania kierowcami:
    // abstract fun driverDao(): DriverDao
}
