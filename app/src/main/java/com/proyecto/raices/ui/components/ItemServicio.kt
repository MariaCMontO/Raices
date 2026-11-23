package com.proyecto.raices.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.raices.R
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.ui.screens.PantallaGestionServicio
import com.proyecto.raices.viewmodel.ServicioViewModel

@Composable
fun ItemServicio(
    servicioVM: ServicioViewModel,
    servicio: ServicioEntity,
    tipoUsuario: String,
    onClick: (() -> Unit)? = null,
    generoUsuario: String,
    correoUsuario: String,
    onDelete: (Int) -> Unit,
    ) {
    val fotoAnfi = if (generoUsuario == "F") R.drawable.mujer_user else R.drawable.hombre_user
    var mostrarGestion by remember { mutableStateOf(false) }
    var mostrarConfirmacionEliminar by remember { mutableStateOf(false) }
    val servicioVM = servicioVM

    val categoria = servicio.categoria

    val imagen = when (categoria) {
        CategoriaServicio.HISTORIA -> {
            R.drawable.historia
        }

        CategoriaServicio.MEDICINA -> {
            R.drawable.medicina
        }

        CategoriaServicio.ARTESANIAS -> {
            R.drawable.artesanias
        }

        CategoriaServicio.RECORRIDOS -> {
            R.drawable.recorridos
        }

        CategoriaServicio.AGRICULTURA -> {
            R.drawable.agricultura
        }

        else -> {
            R.drawable.gastronomia
        }
    }

    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp)
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = "Imagen Servicio",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = fotoAnfi),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFFE6DCCB), CircleShape)
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = servicio.nombreAnfitrion,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .padding(start = 16.dp),
                text = "Nombre: ${servicio.nombre}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .padding(start = 16.dp),
                text = "Categoría: ${servicio.categoria.tipo}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 40.dp,
                        bottom = 8.dp
                    ),
                text = "Descripción: ${servicio.descripcion} ",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "$${servicio.precio} COP / pers.",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column() {
                    Button(
                        onClick = {
                            if (tipoUsuario == "EXPLORADOR") {

                            } else {
                                mostrarGestion = true;
                            }
                        },
                        modifier = Modifier
                            .width(110.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFA0522D),
                            contentColor = Color.White
                        )
                    ) {
                        val texto = if (tipoUsuario == "EXPLORADOR") "Reservar" else "Editar"
                        Text(texto)
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    if (tipoUsuario != "EXPLORADOR") {
                        Button(
                            onClick = {
                                mostrarConfirmacionEliminar = true;
                            },
                            modifier = Modifier
                                .width(110.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFE91E1E),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }

            if (mostrarGestion) {
                PantallaGestionServicio(
                    servicioVM = servicioVM,
                    servicio = servicio,
                    onCancelar = { mostrarGestion = false },
                    onActualizar = { servicioActualizado ->
                        servicioVM.actualizar(servicioActualizado)

                        mostrarGestion = false
                    },
                    onTransformarCategoria = { servicioVM.transformarACategoriaEnum(it)!! }
                )
            }

            if (mostrarConfirmacionEliminar) {
                AlertDialog(
                    onDismissRequest = { mostrarConfirmacionEliminar = false },
                    title = { Text("¿Eliminar servicio?") },
                    text = { Text("Esta acción no se puede deshacer. Se eliminará \"${servicio.nombre}\".") },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3261E)),
                            onClick = {
                                mostrarConfirmacionEliminar = false
                                onDelete(servicio.id)
                            }
                        ) {
                            Text("Sí, eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarConfirmacionEliminar = false }) {
                            Text("No, volver")
                        }
                    }
                )
            }
        }
    }
}