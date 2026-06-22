package com.aistudio.fleetmanager.mvp.di

import android.content.Context
import androidx.room.Room
import com.aistudio.fleetmanager.mvp.data.AppDatabase
import com.aistudio.fleetmanager.mvp.data.dao.AnalyticsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fleet_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideAnalyticsDao(database: AppDatabase): AnalyticsDao {
        return database.analyticsDao()
    }
}
