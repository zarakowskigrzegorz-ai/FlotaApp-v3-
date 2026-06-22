package com.aistudio.fleetmanager.mvp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aistudio.fleetmanager.mvp.presentation.viewmodel.DriverViewModel

@Composable
fun DriverScreen(viewModel: DriverViewModel = viewModel()) {
    val odometer by viewModel.currentOdometer.collectAsState()
    val profitReport by viewModel.profitReport.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Panel Kierowcy", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 20.dp))

        // --- NASZ RAPORT TESTOWY ---
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        ) {
            Text(
                text = profitReport,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(16.dp)
            )
        }
        // ---------------------------

        OutlinedTextField(
            value = odometer, onValueChange = { viewModel.updateOdometer(it) },
            label = { Text("Stan licznika startowy (km)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true, modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        )

        Button(
            onClick = { viewModel.startTrip() },
            modifier = Modifier.fillMaxWidth().height(70.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("ROZPOCZNIJ TRASĘ", fontSize = 20.sp)
        }
    }
}
