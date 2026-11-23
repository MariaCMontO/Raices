package com.proyecto.raices.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.ui.components.BarrasSuperiorInferior
import com.proyecto.raices.ui.components.chipsUnico
import com.proyecto.raices.viewmodel.ServicioViewModel
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@Composable
fun PantallaAñadirServicio(
    navController: NavController,
    servicioVM: ServicioViewModel,
    usuarioVM: viewmodelUsuarios
) {
    val usuario = usuarioVM.usuarioActivo ?: return
    val tipoUsuario = usuario.tipoUsuario

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("")}
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    val correoAnfitrion = usuario.correo
    val nombreAnfitrion = usuario.nombre
    val categoriasStr = servicioVM.categoriasStr

    var error by remember { mutableStateOf("") }

    val coloresCampo = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color(0xFF87736B),
        unfocusedContainerColor = Color.White
    )

    val context = LocalContext.current
    var enviando by remember { mutableStateOf(false) }
    val respuestaServidor = servicioVM.mensaje.value

    LaunchedEffect(respuestaServidor) {
        if (!enviando || respuestaServidor.isEmpty()) return@LaunchedEffect

        if (respuestaServidor == "OK") {
            Toast.makeText(context, "Servicio creado correctamente", Toast.LENGTH_LONG).show()

            nombre = ""
            descripcion = ""
            categoria = ""
            precio = ""
            error = ""

            navController.navigate("mis-servicios") {
                popUpTo("añadir-servicio") { inclusive = true }
                launchSingleTop = true
            }
        } else {
            error = respuestaServidor
        }

        enviando = false
        servicioVM.mensaje.value = ""   // evita que un "OK" viejo vuelva a disparar esto
    }

    BarrasSuperiorInferior(
        navController = navController,
        tipoUsuario = tipoUsuario

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("Añadir Servicio")
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

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF823B18)),
                onClick = {
                    val precioNumerico = precio.trim().toDoubleOrNull()

                    if (nombre.isBlank() || descripcion.isBlank() ||
                        precioNumerico == null || categoria.isBlank()
                    ) {
                        error = "Debe llenar todos los campos"

                    } else {
                        error = ""
                        val servicioCreado = ServicioEntity(
                            nombre = nombre.trim(),
                            descripcion = descripcion.trim(),
                            categoria = servicioVM.transformarACategoriaEnum(categoria.trim())!!,
                            nombreAnfitrion = nombreAnfitrion,
                            correoAnfitrion = correoAnfitrion,
                            precio = precioNumerico
                        )

                        enviando = true
                        servicioVM.mensaje.value = ""
                        servicioVM.registrar(servicioCreado)
                    }
                }
            ) {
                Text("Crear")
            }

            if (error.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = error, color = Color.Red, fontSize = 13.sp)
            }
        }
    }
}
