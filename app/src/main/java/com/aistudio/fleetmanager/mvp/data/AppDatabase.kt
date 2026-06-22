package com.aistudio.fleetmanager.mvp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aistudio.fleetmanager.mvp.data.model.TruckEntity
import com.aistudio.fleetmanager.mvp.data.model.DriverEntity
import com.aistudio.fleetmanager.mvp.data.model.TripEntity
import com.aistudio.fleetmanager.mvp.data.model.CostEntity
import com.aistudio.fleetmanager.mvp.data.dao.AnalyticsDao

@Database(
    entities = [TruckEntity::class, DriverEntity::class, TripEntity::class, CostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun analyticsDao(): AnalyticsDao
}
