package com.aistudio.fleetmanager.mvp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aistudio.fleetmanager.mvp.data.DatabaseSeeder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverViewModel @Inject constructor(
    private val seeder: DatabaseSeeder
) : ViewModel() {

    private val _currentOdometer = MutableStateFlow("")
    val currentOdometer: StateFlow<String> = _currentOdometer

    private val _profitReport = MutableStateFlow("Trwa liczenie zysków pod maską...")
    val profitReport: StateFlow<String> = _profitReport

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = seeder.runTestAndGetReport()
            _profitReport.value = result
        }
    }

    fun updateOdometer(newValue: String) { _currentOdometer.value = newValue }
    fun startTrip() { }
}
