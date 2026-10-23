package com.proyecto.raices.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class PantallaDestino(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
) {
    object Explorar :
        PantallaDestino(
            ruta = "explorar/{tipoUsuario}",
            titulo = "Explorar",
            icono = Icons.Outlined.Explore
        )

    object MisViajes :
        PantallaDestino(
            ruta = "viajes/{tipoUsuario}",
            titulo = "Mis Viajes",
            icono = Icons.Outlined.Map
        )

    object MisServicios :
        PantallaDestino(
            ruta = "mis-servicios/{tipoUsuario}",
            titulo = "Mis Servicios",
            icono = Icons.Outlined.Explore
        )

    object AñadirServicio :
        PantallaDestino(
            ruta = "añadir-servicio/{tipoUsuario}",
            titulo = "Añadir Servicio",
            icono = Icons.Outlined.Map
        )

    object Perfil :
        PantallaDestino(
            ruta = "perfil",
            titulo = "Perfil",
            icono = Icons.Outlined.Person
        )
}

@Composable
fun AppBarraInferior(
    rutaActual: String?,
    tipoUsuario: String,
    onPantallaSeleccionada: (PantallaDestino) -> Unit
) {
    val botones = if (tipoUsuario == "EXPLORADOR") {
        listOf(
            PantallaDestino.Explorar,
            PantallaDestino.MisViajes,
            PantallaDestino.Perfil
        )
    } else {
        listOf(
            PantallaDestino.MisServicios,
            PantallaDestino.AñadirServicio,
            PantallaDestino.Perfil
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFBF3E4))
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        botones.forEach { pantalla ->
            val rutaBase = pantalla.ruta.substringBefore("/{")

            val esActivo = rutaActual?.startsWith(rutaBase) == true

            Button(
                onClick = { onPantallaSeleccionada(pantalla) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (esActivo) Color(0xFFCFE99F) else Color.Transparent,
                    contentColor = if (esActivo) Color(0xFF546A2E) else Color.Black
                ),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = pantalla.icono,
<<<<<<< Updated upstream
                        contentDescription = pantalla.titulo
=======
                        contentDescription = pantalla.titulo,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = pantalla.titulo,
                        fontSize = 14.sp,
                        maxLines = 1,
                        softWrap = false
>>>>>>> Stashed changes
                    )
                    Text(pantalla.titulo)
                }
            }
        }
    }
}