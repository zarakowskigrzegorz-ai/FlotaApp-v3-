package com.aistudio.fleetmanager.mvp.data

import com.aistudio.fleetmanager.mvp.data.dao.AnalyticsDao
import com.aistudio.fleetmanager.mvp.data.model.*
import com.aistudio.fleetmanager.mvp.data.parser.CostImportParser
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val analyticsDao: AnalyticsDao
) {
    suspend fun runTestAndGetReport(): String {
        try {
            // 1. Dodajemy kierowcę i ciągnik
            analyticsDao.insertDriver(DriverEntity("PESEL123", "Jan", "Kowalski", 4000.0, 0.50))
            analyticsDao.insertTruck(TruckEntity("PO12345", "PO98765", "Scania", "R450", 28.0, 5000.0))

            // 2. Parsujemy sztuczne wiersze z faktury AS24
            val parser = CostImportParser()
            val cost1 = parser.parseCsvRow("2026-06-22 10:00;CARD_AS24_99812;FUEL;400.0;1800.0;Stacja AS24 Berlin")
            val cost2 = parser.parseCsvRow("2026-06-22 14:30;CARD_AS24_99812;TOLL;0.0;250.0;Autostrada A2")
            
            if (cost1 != null) analyticsDao.insertCost(cost1)
            if (cost2 != null) analyticsDao.insertCost(cost2)

            // 3. Dodajemy trasę, która trwała 21-23 czerwca (czyli koszty z 22 czerwca się "złapią")
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val start = sdf.parse("2026-06-21")?.time ?: 0L
            val end = sdf.parse("2026-06-23")?.time ?: 0L

            analyticsDao.insertTrip(
                TripEntity(
                    cargoName = "Elektronika", startLocation = "Poznań", endLocation = "Berlin",
                    priceEur = 1500.0, fkTruckId = "PO12345", fkDriverId = "PESEL123",
                    startOdometer = 100000, endOdometer = 101200, startDate = start, endDate = end
                )
            )

            // 4. Pobieramy gotowy raport zysków z naszej analityki SQL
            val reports = analyticsDao.getTripProfitabilityReport()
            if (reports.isNotEmpty()) {
                val report = reports.first()
                return "TEST LOGIKI BIZNESOWEJ:\n" +
                       "Zarobek z frachtu: ${report.przychodPln} PLN\n" +
                       "Odjęte koszty (Paliwo+Myto): ${report.kosztyZmiennePln} PLN\n\n" +
                       "ZYSK NA CZYSTO: ${report.czystyZyskPln} PLN!"
            }
            return "Brak raportu do wyświetlenia."
        } catch (e: Exception) {
            return "Błąd bazy: ${e.message}"
        }
    }
}
