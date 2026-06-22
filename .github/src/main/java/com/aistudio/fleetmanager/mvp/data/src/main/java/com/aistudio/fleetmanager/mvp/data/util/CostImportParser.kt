package com.aistudio.fleetmanager.mvp.data.util

import com.aistudio.fleetmanager.mvp.data.model.CostEntity
import java.text.SimpleDateFormat
import java.util.Locale

class CostImportParser {

    // Przykładowe mapowanie: Numer karty paliwowej/OBU -> Numer rejestracyjny ciągnika.
    // W docelowym systemie te powiązania będą pobierane bezpośrednio z bazy danych.
    private val cardToTruckMap = mapOf(
        "CARD_AS24_99812" to "PO12345",
        "OBU_EUROWAG_7721" to "PO12345",
        "CARD_AS24_88123" to "WI9876X"
    )

    /**
     * Parsuje pojedynczą linię z pliku CSV dostawcy i konwertuje ją na obiekt bazy danych CostEntity.
     * Oczekiwany format linii: Data;IdentyfikatorKarty;TypKosztu;Litry;KwotaNetto
     * Przykład: "2026-06-22 11:15;CARD_AS24_99812;FUEL;350.0;1550.0"
     */
    fun parseCsvRow(csvRow: String): CostEntity? {
        return try {
            val tokens = csvRow.split(";")
            if (tokens.size < 5) return null

            val rawDate = tokens[0]
            val cardId = tokens[1]
            val type = tokens[2] // FUEL, ADBLUE, TOLL, SERVICE
            val liters = tokens[3].toDoubleOrNull()
            val priceNetto = tokens[4].toDoubleOrNull() ?: 0.0

            // Konwersja daty tekstowej na znacznik czasu (Long)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val dateTimestamp = dateFormat.parse(rawDate)?.time ?: System.currentTimeMillis()

            // Kluczowe powiązanie: dopasowanie rejestracji pojazdu do użytej karty
            val matchedTruckId = cardToTruckMap[cardId] ?: "NIEZNANY_POJAZD"

            CostEntity(
                fkTruckId = matchedTruckId,
                transactionDate = dateTimestamp,
                costType = type,
                amountNetto = priceNetto,
                amountBrutto = priceNetto * 1.23, // Automatyczne szacowanie kwoty brutto
                volumeLiters = liters,
                locationOrProvider = "Import: $cardId"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
