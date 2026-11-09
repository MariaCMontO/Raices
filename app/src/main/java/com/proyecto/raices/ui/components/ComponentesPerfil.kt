package com.proyecto.raices.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.raices.data.model.Usuario

// Colores propios de este archivo (los de cada perfil son privados)
private val FondoPantalla = Color(0xFFf3f3f0)
private val Marron = Color(0xFF823B18)
private val TextoOscuro = Color(0xFF2B2118)
private val TextoSecundario = Color(0xFF5A4E45)

/** Fila clickeable con ícono, texto y flecha, para las listas de opciones del perfil. */
@Composable
fun OpcionPerfil(icono: ImageVector, texto: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = Marron,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            color = TextoOscuro
        )
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = TextoSecundario
        )
    }
}

@Composable
private fun CampoContraseña(
    value: String,
    onValueChange: (String) -> Unit,
    etiqueta: String,
    visible: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(etiqueta, fontSize = 14.sp) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFF87736B),
            unfocusedContainerColor = Color.White
        ),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

/** Cambiar contraseña (pidiendo la actual) y eliminar cuenta con confirmación. */
@Composable
fun DialogoCuenta(
    usuario: Usuario,
    onCancelar: () -> Unit,
    onCambiarContraseña: (Usuario) -> Unit,
    onEliminarCuenta: () -> Unit
) {
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    var confirmarEliminar by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = FondoPantalla,
        title = {
            Text(
                text = "Cuenta y privacidad",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Text(text = "Correo Electronico", fontSize = 16.sp)
                OutlinedTextField(
                    value = usuario.correo,
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Cambiar contraseña", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (visible) "Ocultar contraseñas" else "Mostrar contraseñas"
                        )
                    }
                }

                CampoContraseña(actual, { actual = it }, "Contraseña actual", visible)
                Spacer(modifier = Modifier.height(8.dp))
                CampoContraseña(nueva, { nueva = it }, "Nueva contraseña", visible)
                Spacer(modifier = Modifier.height(8.dp))
                CampoContraseña(confirmar, { confirmar = it }, "Confirmar nueva contraseña", visible)

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = Color.Red, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(onClick = { confirmarEliminar = true }) {
                    Text("Eliminar mi cuenta", color = Color.Red, fontWeight = FontWeight.SemiBold)
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Marron),
                onClick = {
                    when {
                        actual != usuario.contraseña ->
                            error = "La contraseña actual no es correcta"
                        nueva.length < 3 ->
                            error = "La nueva contraseña debe tener al menos 3 caracteres"
                        nueva != confirmar ->
                            error = "Las contraseñas nuevas no coinciden"
                        else ->
                            onCambiarContraseña(usuario.copy(contraseña = nueva))
                    }
                }
            ) { Text("Cambiar contraseña") }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cerrar", color = Marron) }
        }
    )

    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            containerColor = FondoPantalla,
            title = { Text("¿Eliminar tu cuenta?", fontWeight = FontWeight.Bold, color = TextoOscuro) },
            text = { Text("Esta acción no se puede deshacer. Se borrarán todos tus datos.") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    onClick = onEliminarCuenta
                ) { Text("Sí, eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { confirmarEliminar = false }) {
                    Text("Cancelar", color = Marron)
                }
            }
        )
    }
}