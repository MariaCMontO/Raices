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
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Interests
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.R
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.ui.components.BarrasSuperiorInferior
import com.proyecto.raices.ui.components.DialogoCuenta
import com.proyecto.raices.ui.components.OpcionPerfil
import com.proyecto.raices.ui.components.chips
import com.proyecto.raices.ui.components.chipsUnico
import com.proyecto.raices.viewmodel.viewmodelUsuarios

// ---------- Paleta de colores ----------
private val FondoPantalla = Color(0xFFf3f3f0)
private val FondoCard = Color(0xFFF3EBDD)
private val FondoLista = Color(0xFFF8F1E4)
private val Marron = Color(0xFF823B18)
private val MarronFecha = Color(0xFFA0522D)
private val VerdeOliva = Color(0xFF4F6B2F)
private val TextoOscuro = Color(0xFF2B2118)
private val TextoSecundario = Color(0xFF5A4E45)
private val ChipFondo = Color(0xFFE6DCCB)

@Composable
fun PerfilExplorador(
    navController: NavController,
    viewmodel: viewmodelUsuarios,
) {

    var mostrarDialogo by remember { mutableStateOf(false) }
    var mostrarPreferencias by remember { mutableStateOf(false) }
    var mostrarCuenta by remember { mutableStateOf(false) }

    // Si no hay sesión (p. ej. tras cerrarSesion) no se dibuja nada
    val usuario = viewmodel.usuarioActivo ?: return
    val tipoUsuario = usuario.tipoUsuario

    // Datos del usuario
    val nombre = usuario.nombre
    val ubicacion = usuario.direccion
    val nivel = "Nivel 1"
    val sobreMi = usuario.sobreMi.ifBlank { "Aún no has escrito sobre ti" }
    val intereses = usuario.intereses ?: emptyList()
    val lenguas = usuario.lenguas
    val foto = if (usuario.genero == "F") R.drawable.mujer_user else R.drawable.hombre_user

    // Datos quemados de la experiencia registrada.
    val dia = "DÍA"
    val numero = "0"
    val tituloEncuentro = "Titulo de encuentro"
    val lugarEncuentro = "Lugar de encuentro"

    BarrasSuperiorInferior(
        navController = navController,
        tipoUsuario = tipoUsuario
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(FondoPantalla)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ---------- Foto con insignia de hoja ----------
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = painterResource(id = foto),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color(0xFFE6DCCB), CircleShape)
                    )
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .background(VerdeOliva, CircleShape)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = nombre,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    color = TextoOscuro
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = TextoSecundario,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = ubicacion, fontSize = 13.sp, color = TextoSecundario)
                    Text(text = "  ·  ", fontSize = 13.sp, color = TextoSecundario)
                    Text(
                        text = nivel,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VerdeOliva
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = sobreMi,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    color = TextoSecundario,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

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

                Spacer(modifier = Modifier.height(28.dp))

                // ---------- Próximo encuentro ----------
                Text(
                    text = "Próximo encuentro",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    color = TextoOscuro
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FondoCard)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .size(width = 48.dp, height = 52.dp)
                                .background(MarronFecha, RoundedCornerShape(8.dp)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dia,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = numero,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = tituloEncuentro,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    tint = TextoSecundario,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = lugarEncuentro,
                                    fontSize = 13.sp,
                                    color = TextoSecundario
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---------- Lenguas ----------
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FondoCard)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
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

                // ---------- Intereses ----------
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FondoCard)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Interests,
                                contentDescription = null,
                                tint = Marron,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Intereses personales:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoOscuro
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // FlowRow para que los intereses pasen a otra línea si no caben
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            intereses.forEach { interes ->
                                Text(
                                    text = interes,
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

                // ---------- Opciones ----------
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FondoLista)
                ) {
                    Column {
                        OpcionPerfil(Icons.Outlined.Tune, "Preferencias de viaje") {
                            mostrarPreferencias = true
                        }
                        HorizontalDivider(color = Color(0xFFEFE5D2))
                        OpcionPerfil(Icons.Outlined.Shield, "Cuenta y privacidad") {
                            mostrarCuenta = true
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
    }

    if (mostrarDialogo) {
        DialogoEditarPerfil(
            usuario = usuario,
            onCancelar = { mostrarDialogo = false },
            onGuardar = { actualizado ->
                viewmodel.actualizarPersona(actualizado)
                mostrarDialogo = false
            }
        )
    }

    if (mostrarPreferencias) {
        DialogoPreferencias(
            usuario = usuario,
            onCancelar = { mostrarPreferencias = false },
            onGuardar = { actualizado ->
                viewmodel.actualizarPersona(actualizado)
                mostrarPreferencias = false
            }
        )
    }

    if (mostrarCuenta) {
        DialogoCuenta(
            usuario = usuario,
            onCancelar = { mostrarCuenta = false },
            onCambiarContraseña = { actualizado ->
                viewmodel.actualizarPersona(actualizado)
                mostrarCuenta = false
            },
            onEliminarCuenta = {
                navController.navigate("iniciosesion") { popUpTo(0) }
                viewmodel.eliminarUsuario(usuario)
            }
        )
    }
}

@Composable
private fun DialogoEditarPerfil(
    usuario: Usuario,
    onCancelar: () -> Unit,
    onGuardar: (Usuario) -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var telefono by remember { mutableStateOf(usuario.telefono) }
    var direccion by remember { mutableStateOf(usuario.direccion) }
    var sobreMi by remember { mutableStateOf(usuario.sobreMi) }
    var genero by remember { mutableStateOf(usuario.genero) }
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
                    if (nombre.isBlank() || telefono.isBlank() || direccion.isBlank() ||
                        sobreMi.isBlank() || genero.isBlank()
                    ) {
                        error = "Debe llenar todos los campos"
                    } else {
                        onGuardar(
                            usuario.copy(
                                nombre = nombre.trim(),
                                telefono = telefono.trim(),
                                direccion = direccion.trim(),
                                sobreMi = sobreMi.trim(),
                                genero = genero
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
private fun DialogoPreferencias(
    usuario: Usuario,
    onCancelar: () -> Unit,
    onGuardar: (Usuario) -> Unit
) {
    var intereses by remember { mutableStateOf<List<String>>(usuario.intereses?.toList() ?: emptyList()) }
    var lenguas by remember { mutableStateOf<List<String>>(usuario.lenguas.toList()) }
    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = FondoPantalla,
        title = {
            Text(
                text = "Preferencias de viaje",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(text = "Intereses", fontSize = 16.sp)
                chips(
                    value = intereses,
                    onValueChange = { intereses = it },
                    opciones = listOf("Deporte", "Música", "Tecnología", "Arte", "Viajes")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Lenguas", fontSize = 16.sp)
                chips(
                    value = lenguas,
                    onValueChange = { lenguas = it },
                    opciones = listOf("Español", "Ingles", "Frances", "Italiano")
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
                    if (intereses.isEmpty() || lenguas.isEmpty()) {
                        error = "Selecciona al menos un interés y una lengua"
                    } else {
                        onGuardar(
                            usuario.copy(
                                intereses = intereses.toMutableList(),
                                lenguas = lenguas.toMutableList()
                            )
                        )
                    }
                }
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar", color = Marron) }
        }
    )
}