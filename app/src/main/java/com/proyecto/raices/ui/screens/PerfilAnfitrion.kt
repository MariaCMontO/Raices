package com.proyecto.raices.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.R
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.ui.components.BarrasSuperiorInferior
import com.proyecto.raices.ui.components.chips
import com.proyecto.raices.ui.components.chipsUnico
import com.proyecto.raices.viewmodel.viewmodelUsuarios

// ---------- Paleta de colores ----------
private val FondoPantalla = Color(0xFFf3f3f0)
private val FondoCard = Color(0xFFF3EBDD)
private val Marron = Color(0xFF823B18)
private val VerdeOliva = Color(0xFF4F6B2F)
private val VerdeIndicador = Color(0xFFCDEB9B)
private val TextoOscuro = Color(0xFF2B2118)
private val ChipFondo = Color(0xFFE6DCCB)

@Composable
fun PerfilAnfitrion(
    navController: NavController,
    viewmodel: viewmodelUsuarios,
    tipoUsuario: String
) {

    var mostrarDialogo by remember { mutableStateOf(false) }

    // Si no hay sesión (p. ej. tras cerrarSesion) no se dibuja nada
    val usuario = viewmodel.usuarioActivo ?: return

    val nombre = usuario.nombre
    val ubicacion = usuario.direccion
    val oficio = usuario.oficio?.takeIf { it.isNotBlank() } ?: "Anfitrión"
    val viajeros = 0
    val talleres = 0
    val lenguas = usuario.lenguas
    val sobreMi = usuario.sobreMi
    val foto = if (usuario.genero == "F") R.drawable.mujer_user else R.drawable.hombre_user

    BarrasSuperiorInferior(
        navController = navController,
        tipoUsuario = tipoUsuario

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(FondoPantalla)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // ---------- Card principal ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FondoCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Foto con borde y check de verificado
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = foto),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(3.dp, Marron, CircleShape)
                        )
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Verificado",
                            tint = VerdeOliva,
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.White, CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = nombre,
                        fontSize = 22.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = TextoOscuro
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = Marron,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = ubicacion,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextoOscuro,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Text(text = "  •  ", fontSize = 13.sp, color = Marron)
                        Text(
                            text = oficio,
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            color = Marron,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Estadísticas
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Estadistica(valor = viajeros.toString(), etiqueta = "Viajeros")
                            Estadistica(valor = talleres.toString(), etiqueta = "Talleres")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { mostrarDialogo = true },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Marron),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Editar perfil",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // ---------- Card "Sobre mí y mi legado" ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FondoCard)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.MenuBook,
                            contentDescription = null,
                            tint = Marron
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sobre mí y mi legado",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "\"$sobreMi\"",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 15.sp,
                            lineHeight = 26.sp,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            color = TextoOscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Translate,
                            contentDescription = null,
                            tint = Marron,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Lenguas de enseñanza:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextoOscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // FlowRow para que las lenguas pasen a otra línea si no caben
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        lenguas.forEach { idioma ->
                            Text(
                                text = idioma,
                                fontSize = 12.sp,
                                color = TextoOscuro,
                                modifier = Modifier
                                    .background(ChipFondo, RoundedCornerShape(50))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- Botón cerrar sesión ----------
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Marron),
                onClick = {
                    viewmodel.cerrarSesion()
                    navController.navigate("iniciosesion") { popUpTo(0) }
                }
            ) {
                Text(text = "Cerrar Sesion", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (mostrarDialogo) {
        DialogoEditarAnfitrion(
            usuario = usuario,
            onCancelar = { mostrarDialogo = false },
            onGuardar = { actualizado ->
                viewmodel.actualizarPersona(actualizado)
                mostrarDialogo = false
            }
        )
    }
}

@Composable
private fun DialogoEditarAnfitrion(
    usuario: Usuario,
    onCancelar: () -> Unit,
    onGuardar: (Usuario) -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var telefono by remember { mutableStateOf(usuario.telefono) }
    var contraseña by remember { mutableStateOf(usuario.contraseña) }
    var direccion by remember { mutableStateOf(usuario.direccion) }
    var sobreMi by remember { mutableStateOf(usuario.sobreMi) }
    var genero by remember { mutableStateOf(usuario.genero) }
    var lenguas by remember { mutableStateOf<List<String>>(usuario.lenguas.toList()) }
    var oficio by remember { mutableStateOf(usuario.oficio ?: "") }
    var contraseñaVisible by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val coloresCampo = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color(0xFF87736B),
        unfocusedContainerColor = Color.White
    )

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = FondoPantalla,
        title = {
            Text(
                text = "Editar perfil",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Text(text = "Nombre Completo", fontSize = 16.sp)
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Ingresa tu Nombre Completo", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )

                Spacer(modifier = Modifier.height(12.dp))

                // El correo identifica al usuario, por eso no se puede editar
                Text(text = "Correo Electronico", fontSize = 16.sp)
                OutlinedTextField(
                    value = usuario.correo,
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Contraseña", fontSize = 16.sp)
                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    label = { Text("Crea una contraseña", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo,
                    visualTransformation = if (contraseñaVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (contraseñaVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (contraseñaVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        IconButton(onClick = { contraseñaVisible = !contraseñaVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Telefono", fontSize = 16.sp)
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Telefono", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Direccion", fontSize = 16.sp)
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Direccion", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Sobre Mi", fontSize = 16.sp)
                OutlinedTextField(
                    value = sobreMi,
                    onValueChange = { sobreMi = it },
                    label = { Text("Sobre mi", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo,
                    minLines = 3,
                    maxLines = 6
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Genero", fontSize = 16.sp)
                chipsUnico(
                    value = genero,
                    onValueChange = { genero = it },
                    opciones = listOf("F", "M")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Lenguas", fontSize = 16.sp)
                chips(
                    value = lenguas,
                    onValueChange = { lenguas = it },
                    opciones = listOf("Español", "Ingles", "Frances", "Italiano")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Oficio", fontSize = 16.sp)
                OutlinedTextField(
                    value = oficio,
                    onValueChange = { oficio = it },
                    label = { Text("Escribe que deseas compartir", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo,
                    maxLines = 5
                )

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = Color.Red, fontSize = 13.sp)
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Marron),
                onClick = {
                    if (nombre.isBlank() || telefono.isBlank() || contraseña.isBlank() ||
                        direccion.isBlank() || oficio.isBlank() || sobreMi.isBlank() ||
                        genero.isBlank() || lenguas.isEmpty()
                    ) {
                        error = "Debe llenar todos los campos"
                    } else {
                        onGuardar(
                            usuario.copy(
                                nombre = nombre.trim(),
                                telefono = telefono.trim(),
                                contraseña = contraseña,
                                direccion = direccion.trim(),
                                oficio = oficio.trim(),
                                sobreMi = sobreMi.trim(),
                                genero = genero,
                                lenguas = lenguas.toMutableList()
                            )
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar", color = Marron)
            }
        }
    )
}

@Composable
private fun Estadistica(valor: String, etiqueta: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = VerdeOliva
        )
        Text(text = etiqueta, fontSize = 11.sp, color = Color(0xFF5A4E45))
    }
}