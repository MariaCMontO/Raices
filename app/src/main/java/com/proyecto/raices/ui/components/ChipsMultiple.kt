package com.proyecto.raices.ui.components

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chips(value: List<String>, onValueChange: (List<String>) -> Unit, opciones: List<String>) {
    val opciones = opciones

    FlowRow (

    ) {
        opciones.forEach { opcion ->
            val selected = value.contains(opcion)

            FilterChip(
                selected = selected,
                onClick = {
                    val nuevos = if (selected) value - opcion else value + opcion
                    onValueChange(nuevos)
                },
                label = { Text(opcion) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE2E2E2),
                    containerColor = Color(0xFFFFF8EF),
                )
            )
        }
    }
}