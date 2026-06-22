package com.aistudio.fleetmanager.mvp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DriverViewModel @Inject constructor() : ViewModel() {

    // Zmienna przechowująca wpisany stan licznika (Flow pozwala ekranowi reagować na zmiany na żywo)
    private val _currentOdometer = MutableStateFlow("")
    val currentOdometer: StateFlow<String> = _currentOdometer

    // Funkcja wywoływana, gdy kierowca wpisuje coś na klawiaturze telefonu
    fun updateOdometer(newValue: String) {
        _currentOdometer.value = newValue
    }

    // Funkcja wywoływana po kliknięciu wielkiego przycisku "Rozpocznij Trasę"
    fun startTrip() {
        val odometerValue = _currentOdometer.value.toIntOrNull() ?: 0
        // W przyszłości ten kod wywoła bazę danych i zapisze tam nową trasę (TripEntity)
        println("Rejestracja trasy... Stan licznika start: $odometerValue")
    }
}
