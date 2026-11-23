package com.proyecto.raices.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.ui.components.chipsUnico
import com.proyecto.raices.viewmodel.ServicioViewModel


@Composable
fun PantallaGestionServicio(
    servicioVM: ServicioViewModel,
    servicio: ServicioEntity,
    onCancelar: () -> Unit,
    onActualizar: (ServicioEntity) -> Unit,
    onTransformarCategoria: (String) -> CategoriaServicio
) {
    var id by remember { mutableIntStateOf(servicio.id) }
    var nombre by remember { mutableStateOf(servicio.nombre) }
    var categoria by remember { mutableStateOf(servicio.categoria.tipo) }
    var descripcion by remember { mutableStateOf(servicio.descripcion) }
    var precio by remember { mutableStateOf(servicio.precio.toString()) }

    val categoriasStr = servicioVM.categoriasStr

    var error by remember { mutableStateOf("") }

    val coloresCampo = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color(0xFF87736B),
        unfocusedContainerColor = Color.White
    )

    AlertDialog(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Desactiva las restricciones de ancho por defecto
        ),
        onDismissRequest = onCancelar,
        containerColor = Color(0xFFf3f3f0),
        title = {
            Text(
                text = "Editar Servicio",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2118)
            );
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Text(text = "Información sobre el servicio", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = id.toString(),
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    label = { Text("Id", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo,
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo,
                    minLines = 3,
                    maxLines = 6
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )
                Spacer(modifier = Modifier.height(12.dp))

                chipsUnico(
                    value = categoria,
                    onValueChange = { categoria = it },
                    opciones = categoriasStr
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = Color.Red, fontSize = 13.sp)
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF823B18)),
                onClick = {
                    val precioNumerico = precio.trim().toDoubleOrNull()

                    if (nombre.isBlank() || descripcion.isBlank() ||
                        precio == null || categoria.isBlank()
                    ) {
                        error = "Debe llenar todos los campos"

                    } else {
                        onActualizar(
                            ServicioEntity(
                                id = servicio.id,
                                nombre = nombre.trim(),
                                descripcion = descripcion.trim(),
                                categoria = onTransformarCategoria(categoria.trim()),
                                nombreAnfitrion = servicio.nombreAnfitrion,
                                correoAnfitrion = servicio.correoAnfitrion,
                                precio = precio.trim().toDouble()
                            )
                        )
                    }
                }
            ) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar", color = Color(0xFF823B18))
            }
        }
    )
}