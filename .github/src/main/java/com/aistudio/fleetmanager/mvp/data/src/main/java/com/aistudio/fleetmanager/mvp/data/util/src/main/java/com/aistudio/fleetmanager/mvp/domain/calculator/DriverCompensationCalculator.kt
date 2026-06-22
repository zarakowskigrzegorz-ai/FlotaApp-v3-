package com.aistudio.fleetmanager.mvp.domain.calculator

import java.util.concurrent.TimeUnit

// Statusy aktywności z tachografu lub wpisywane ręcznie przez kierowcę
enum class DriverStatus {
    DRIVING,
    WORK,
    AVAILABILITY,
    REST
}

// Pojedynczy wpis o aktywności w danym kraju
data class ActivityRecord(
    val startTime: Long,
    val endTime: Long,
    val status: DriverStatus,
    val countryCode: String // np. "PL", "DE", "FR"
)

class DriverCompensationCalculator {

    // Stawki diet dla poszczególnych krajów (wartości przykładowe w PLN)
    private val countryDietRates = mapOf(
        "PL" to 45.0,  // Polska
        "DE" to 220.0, // Niemcy
        "FR" to 240.0  // Francja
    )

    /**
     * Oblicza sumaryczną dietę na podstawie listy zarejestrowanych aktywności.
     */
    fun calculateTotalDiet(activities: List<ActivityRecord>): Double {
        var totalDiet = 0.0

        // Grupowanie wszystkich logów po kodzie kraju
        val timePerCountry = activities.groupBy { it.countryCode }

        for ((country, records) in timePerCountry) {
            var totalDurationMillis = 0L
            for (record in records) {
                totalDurationMillis += (record.endTime - record.startTime)
            }

            // Przeliczenie milisekund na pełne godziny
            val totalHours = TimeUnit.MILLISECONDS.toHours(totalDurationMillis)
            val dayRate = countryDietRates[country] ?: 45.0 // Domyślnie PL

            val days = totalHours / 24
            val remainingHours = totalHours % 24

            // Pełne doby
            totalDiet += days * dayRate

            // Ułamki dób (według przykładowego uproszczonego algorytmu)
            totalDiet += when {
                remainingHours in 1..8 -> dayRate / 3.0
                remainingHours in 9..12 -> dayRate / 2.0
                remainingHours > 12 -> dayRate
                else -> 0.0
            }
        }

        return totalDiet
    }
}
