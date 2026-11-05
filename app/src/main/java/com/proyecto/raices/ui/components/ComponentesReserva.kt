package com.proyecto.raices.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.raices.R
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.Reserva
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToLong


fun imagenCategoria(categoria: CategoriaServicio): Int = when (categoria) {
    CategoriaServicio.HISTORIA -> R.drawable.historia
    CategoriaServicio.MEDICINA -> R.drawable.medicina
    CategoriaServicio.ARTESANIAS -> R.drawable.artesanias
    CategoriaServicio.RECORRIDOS -> R.drawable.recorridos
    CategoriaServicio.AGRICULTURA -> R.drawable.agricultura
    else -> R.drawable.gastronomia
}

fun formatoPrecio(valor: Double): String {
    val numero = NumberFormat.getIntegerInstance(Locale("es", "CO")).format(valor.roundToLong())
    return "$$numero COP"
}


fun formatoFecha(millisUtc: Long): String =
    Instant.ofEpochMilli(millisUtc)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es", "CO")))


@Composable
fun ReservaItem(reserva: Reserva, onCancelar: () -> Unit) {
    var confirmarCancelacion by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = imagenCategoria(reserva.categoria)),
                contentDescription = reserva.nombreServicio,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reserva.nombreServicio,
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = ViajesColores.TextoOscuro
                )
                Text(
                    text = "Con ${reserva.nombreAnfitrion}",
                    fontSize = 13.sp,
                    color = Color(0xFF6B5E52)
                )
                Spacer(Modifier.size(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = ViajesColores.Marron,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(formatoFecha(reserva.fechaReserva), fontSize = 13.sp)
                }
                val personasTxt = if (reserva.personas == 1) "1 persona" else "${reserva.personas} personas"
                Text(
                    text = "$personasTxt · ${formatoPrecio(reserva.total)}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = ViajesColores.VerdeTexto
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { confirmarCancelacion = true }) {
                        Text("Cancelar reserva", color = ViajesColores.Marron, fontSize = 13.sp)
                    }
                }
            }
        }
    }

    if (confirmarCancelacion) {
        AlertDialog(
            onDismissRequest = { confirmarCancelacion = false },
            containerColor = ViajesColores.Fondo,
            title = { Text("¿Cancelar la reserva?", fontFamily = FontFamily.Serif) },
            text = { Text("Se cancelará tu reserva de \"${reserva.nombreServicio}\".") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarCancelacion = false
                    onCancelar()
                }) { Text("Sí, cancelar", color = ViajesColores.Marron, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { confirmarCancelacion = false }) {
                    Text("Volver", color = Color.Gray)
                }
            }
        )
    }
}
