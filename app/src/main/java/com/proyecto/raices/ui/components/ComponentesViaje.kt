package com.proyecto.raices.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.proyecto.raices.data.model.EstadoGrupo
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.viewmodel.ViajesViewModel

object ViajesColores {
    val Fondo = Color(0xFFf3f3f0)
    val Marron = Color(0xFF8B3A1A)
    val BurbujaMia = Color(0xFFA0522D)
    val FondoTarjeta = Color(0xFFFFF3E3)
    val FondoChat = Color(0xFFF3EBDD)
    val VerdeChip = Color(0xFFCFE99F)
    val VerdeTexto = Color(0xFF546A2E)
    val ChipPlaneando = Color(0xFFE6DCCB)
    val TextoOscuro = Color(0xFF2B1B12)
}

private val Serif = FontFamily.Serif

@Composable
fun ChipEstado(estado: EstadoGrupo) {
    val activo = estado == EstadoGrupo.ACTIVO
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (activo) ViajesColores.VerdeChip else ViajesColores.ChipPlaneando)
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = if (activo) estado.etiqueta.uppercase() else estado.etiqueta,
            fontSize = 11.sp,
            fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal,
            color = if (activo) ViajesColores.VerdeTexto else Color(0xFF5A4A3A)
        )
    }
}

@Composable
fun GrupoActivoCard(
    grupo: GrupoViaje,
    usuario: Usuario,
    viajesVM: ViajesViewModel
) {
    val flujoMensajes = remember(grupo.id) { viajesVM.mensajes(grupo.id) }
    val mensajes by flujoMensajes.collectAsState(initial = emptyList())

    var menuAbierto by remember { mutableStateOf(false) }
    var texto by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ViajesColores.FondoTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Column(modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 4.dp, bottom = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChipEstado(grupo.estado)
                    Box {
                        IconButton(onClick = { menuAbierto = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                        }
                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Salir del grupo") },
                                onClick = {
                                    menuAbierto = false
                                    viajesVM.salir(grupo.id, usuario.correo)
                                }
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(end = 12.dp),
                    text = grupo.nombre,
                    fontFamily = Serif,
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Medium,
                    color = ViajesColores.TextoOscuro
                )
            }

            HorizontalDivider(color = ViajesColores.ChipPlaneando)

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                text = "Chat del Grupo",
                fontFamily = Serif,
                fontSize = 16.sp,
                color = ViajesColores.Marron
            )

            ChatMensajes(
                mensajes = mensajes,
                correoUsuario = usuario.correo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(ViajesColores.FondoChat)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                value = texto,
                onValueChange = { texto = it },
                placeholder = { Text("Escribe un mensaje...", fontSize = 14.sp) },
                singleLine = true,
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = ViajesColores.Marron,
                    unfocusedBorderColor = ViajesColores.ChipPlaneando
                ),
                trailingIcon = {
                    IconButton(
                        enabled = texto.isNotBlank(),
                        onClick = {
                            viajesVM.enviarMensaje(grupo.id, usuario, texto)
                            texto = ""
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            tint = ViajesColores.Marron
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ChatMensajes(
    mensajes: List<MensajeGrupo>,
    correoUsuario: String,
    modifier: Modifier = Modifier
) {
    val estado = rememberLazyListState()

    LaunchedEffect(mensajes.size) {
        if (mensajes.isNotEmpty()) estado.animateScrollToItem(mensajes.lastIndex)
    }

    if (mensajes.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("Aún no hay mensajes. ¡Saluda al grupo!", fontSize = 13.sp, color = Color.Gray)
        }
        return
    }

    LazyColumn(
        modifier = modifier,
        state = estado,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(12.dp)
    ) {
        items(mensajes, key = { it.id }) { mensaje ->
            BurbujaMensaje(mensaje, esMio = mensaje.correoAutor == correoUsuario)
        }
    }
}

@Composable
fun BurbujaMensaje(mensaje: MensajeGrupo, esMio: Boolean) {
    if (esMio) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .widthIn(max = 230.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ViajesColores.BurbujaMia)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(mensaje.texto, color = Color.White, fontSize = 14.sp)
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(ViajesColores.BurbujaMia),
                contentAlignment = Alignment.Center
            ) {
                Text("Yo", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    } else {
        val foto = if (mensaje.generoAutor == "F") R.drawable.mujer_user else R.drawable.hombre_user
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = foto),
                contentDescription = mensaje.nombreAutor,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .widthIn(max = 230.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(mensaje.nombreAutor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(mensaje.texto, fontSize = 14.sp, color = ViajesColores.TextoOscuro)
            }
        }
    }
}

@Composable
fun OtroGrupoItem(grupo: GrupoViaje, onUnirse: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    text = grupo.nombre,
                    fontFamily = Serif,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = ViajesColores.TextoOscuro
                )
                ChipEstado(grupo.estado)
            }
            Spacer(Modifier.height(4.dp))
            Text(grupo.descripcion, fontSize = 14.sp, color = Color(0xFF4A4036))
            TextButton(
                onClick = onUnirse,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Text(
                    "Unirse",
                    color = ViajesColores.VerdeTexto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun DialogoCrearGrupo(
    onCancelar: () -> Unit,
    onCrear: (nombre: String, descripcion: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = ViajesColores.Fondo,
        title = { Text("Crear grupo de viaje", fontFamily = Serif, color = ViajesColores.Marron) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del grupo") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    minLines = 2
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = nombre.isNotBlank(),
                onClick = { onCrear(nombre, descripcion) }
            ) { Text("Crear", color = ViajesColores.Marron, fontWeight = FontWeight.Bold) }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar", color = Color.Gray) }
        }
    )
}
