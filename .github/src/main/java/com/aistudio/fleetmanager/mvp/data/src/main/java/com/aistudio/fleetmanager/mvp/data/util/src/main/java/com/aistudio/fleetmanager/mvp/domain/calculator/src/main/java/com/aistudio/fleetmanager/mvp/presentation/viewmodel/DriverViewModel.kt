import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DriverUiState {
    object Loading : DriverUiState
    data class Success(val netProfitPln: Double) : DriverUiState
    data class Error(val message: String) : DriverUiState
}

class DriverViewModel(
    private val databaseSeeder: DatabaseSeeder,
    private val analyticsDao: AnalyticsDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<DriverUiState>(DriverUiState.Loading)
    val uiState: StateFlow<DriverUiState> = _uiState.asStateFlow()

    init {
        performDatabaseSeedingAndFetchReport()
    }

    private fun performDatabaseSeedingAndFetchReport() {
        viewModelScope.launch {
            try {
                // Wywołanie seedowania bazy danych
                databaseSeeder.seedDatabase()

                // Pobranie wyniku z raportu zyskowności i nasłuchiwanie na zmiany
                analyticsDao.getTripProfitabilityReport().collect { report ->
                    if (report != null) {
                        _uiState.value = DriverUiState.Success(report.netProfitPln)
                    } else {
                        _uiState.value = DriverUiState.Success(0.0)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = DriverUiState.Error(
                    e.localizedMessage ?: "Wystąpił nieoczekiwany błąd podczas inicjalizacji bazy danych."
                )
            }
        }
    }
}
