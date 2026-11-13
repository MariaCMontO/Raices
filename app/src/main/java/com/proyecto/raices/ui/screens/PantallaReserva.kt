package com.proyecto.raices.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.ui.components.ChipEstadoTexto
import com.proyecto.raices.ui.components.ViajesColores
import com.proyecto.raices.ui.components.formatoFecha
import com.proyecto.raices.ui.components.formatoPrecio
import com.proyecto.raices.ui.components.imagenCategoria
import com.proyecto.raices.viewmodel.ReservaViewModel
import com.proyecto.raices.viewmodel.ServicioViewModel
import com.proyecto.raices.viewmodel.viewmodelUsuarios
import java.time.LocalDate
import java.time.ZoneOffset

private const val MAX_PERSONAS = 10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaReserva(
    navController: NavController,
    servicioId: Int,
    servicioVM: ServicioViewModel,
    reservaVM: ReservaViewModel,
    usuarioVM: viewmodelUsuarios
) {
    val usuario = usuarioVM.usuarioActivo ?: return
    val context = LocalContext.current

    LaunchedEffect(servicioId) { servicioVM.obtenerPorId(servicioId) }

    val servicio = servicioVM.servicioSeleccionado?.takeIf { it.id == servicioId }

    var fecha by remember { mutableStateOf<Long?>(null) }
    var personas by remember { mutableIntStateOf(1) }
    var notas by remember { mutableStateOf("") }
    var mostrarCalendario by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = ViajesColores.Fondo,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Reservar experiencia",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFBF3E4))
            )
        },
        bottomBar = {
            if (servicio != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFBF3E4))
                        .padding(16.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = fecha != null,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ViajesColores.Marron,
                            contentColor = Color.White
                        ),
                        onClick = {
                            reservaVM.reservar(servicio, usuario, fecha!!, personas, notas)
                            Toast.makeText(context, "¡Reserva confirmada!", Toast.LENGTH_SHORT).show()
                            navController.navigate("viajes") {
                                popUpTo("reservar/{servicioId}") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text(
                            text = if (fecha == null) "Elige una fecha para continuar" else "Confirmar reserva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    ) { padding ->
        if (servicio == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = ViajesColores.Marron) }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painterResource(id = imagenCategoria(servicio.categoria)),
                contentDescription = servicio.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ChipEstadoTexto(servicio.categoria.tipo)
                Text(
                    text = servicio.nombre,
                    fontFamily = FontFamily.Serif,
                    fontSize = 26.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Medium,
                    color = ViajesColores.TextoOscuro
                )
                Text("Con ${servicio.nombreAnfitrion}", fontSize = 14.sp, color = Color(0xFF6B5E52))
                if (servicio.descripcion.isNotBlank()) {
                    Text(servicio.descripcion, fontSize = 14.sp, color = ViajesColores.TextoOscuro)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ViajesColores.FondoTarjeta),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TituloSeccion("Detalles de la reserva")

                    // Fecha
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Fecha", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(50))
                                .background(Color.White)
                                .border(1.dp, ViajesColores.ChipPlaneando, RoundedCornerShape(50))
                                .clickable { mostrarCalendario = true }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.CalendarMonth,
                                contentDescription = null,
                                tint = ViajesColores.Marron
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = fecha?.let { formatoFecha(it) } ?: "Elige una fecha",
                                color = if (fecha == null) Color.Gray else ViajesColores.TextoOscuro,
                                fontSize = 15.sp
                            )
                        }
                    }

                    // Personas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Personas", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Máximo $MAX_PERSONAS", fontSize = 12.sp, color = Color.Gray)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BotonCantidad(
                                icono = Icons.Default.Remove,
                                descripcion = "Menos",
                                habilitado = personas > 1
                            ) { personas-- }
                            Text(
                                text = personas.toString(),
                                modifier = Modifier.width(44.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                fontFamily = FontFamily.Serif,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            BotonCantidad(
                                icono = Icons.Default.Add,
                                descripcion = "Más",
                                habilitado = personas < MAX_PERSONAS
                            ) { personas++ }
                        }
                    }

                    // Notas
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = notas,
                        onValueChange = { notas = it },
                        label = { Text("Notas para el anfitrión (opcional)") },
                        minLines = 2,
                        maxLines = 4,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = ViajesColores.Marron,
                            unfocusedBorderColor = ViajesColores.ChipPlaneando,
                            focusedLabelColor = ViajesColores.Marron
                        )
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TituloSeccion("Resumen")
                    FilaPrecio("Precio por persona", formatoPrecio(servicio.precio))
                    FilaPrecio("Personas", "× $personas")
                    HorizontalDivider(color = ViajesColores.ChipPlaneando)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total", fontFamily = FontFamily.Serif, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(
                            formatoPrecio(servicio.precio * personas),
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = ViajesColores.Marron
                        )
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
        }
    }

    if (mostrarCalendario) {
        val hoyUtc = remember {
            LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        }
        val estadoCalendario = rememberDatePickerState(
            initialSelectedDateMillis = fecha,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis >= hoyUtc
            }
        )
        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(onClick = {
                    fecha = estadoCalendario.selectedDateMillis
                    mostrarCalendario = false
                }) { Text("Aceptar", color = ViajesColores.Marron, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCalendario = false }) { Text("Cancelar", color = Color.Gray) }
            }
        ) {
            DatePicker(state = estadoCalendario)
        }
    }
}

@Composable
private fun TituloSeccion(texto: String) {
    Text(
        text = texto,
        fontFamily = FontFamily.Serif,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = ViajesColores.Marron
    )
}

@Composable
private fun FilaPrecio(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(etiqueta, fontSize = 14.sp, color = Color(0xFF6B5E52))
        Text(valor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun BotonCantidad(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    descripcion: String,
    habilitado: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(if (habilitado) ViajesColores.Marron else ViajesColores.ChipPlaneando)
            .clickable(enabled = habilitado, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icono,
            contentDescription = descripcion,
            tint = if (habilitado) Color.White else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}
