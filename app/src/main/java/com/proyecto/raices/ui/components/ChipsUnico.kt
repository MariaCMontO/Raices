package com.proyecto.raices.ui.components

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun chipsUnico(
    value: String?,                // solo uno
    onValueChange: (String) -> Unit,
    opciones: List<String>
) {
    FlowRow {
        opciones.forEach { opcion ->
            val selected = value == opcion

            FilterChip(
                selected = selected,
                onClick = { onValueChange(opcion) },   // siempre reemplaza
                label = { Text(opcion) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE2E2E2),
                    containerColor = Color(0xFFFFF8EF),
                )
            )
        }
    }
}