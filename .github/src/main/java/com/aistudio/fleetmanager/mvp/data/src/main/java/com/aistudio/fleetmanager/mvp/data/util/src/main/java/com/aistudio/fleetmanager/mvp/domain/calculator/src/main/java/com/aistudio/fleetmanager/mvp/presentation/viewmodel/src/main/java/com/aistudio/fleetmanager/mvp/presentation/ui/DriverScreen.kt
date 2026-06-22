package com.aistudio.fleetmanager.mvp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aistudio.fleetmanager.mvp.presentation.viewmodel.DriverViewModel

@Composable
fun DriverScreen(viewModel: DriverViewModel = viewModel()) {
    // Nasłuchujemy zmian z ViewModelu na żywo
    val odometer by viewModel.currentOdometer.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Panel Kierowcy",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Pole do wpisywania stanu licznika (odpala klawiaturę numeryczną)
        OutlinedTextField(
            value = odometer,
            onValueChange = { viewModel.updateOdometer(it) },
            label = { Text("Stan licznika startowy (km)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Wielki przycisk startu trasy
        Button(
            onClick = { viewModel.startTrip() },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp), // Ekstra wysoki obszar dotykowy
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "ROZPOCZNIJ TRASĘ", fontSize = 20.sp)
        }
    }
}
