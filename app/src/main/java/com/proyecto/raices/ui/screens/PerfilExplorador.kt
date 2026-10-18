package com.proyecto.raices.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.R
import com.proyecto.raices.viewmodel.viewmodelUsuarios
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.proyecto.raices.data.model.Usuario
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

// ---------- Paleta de colores ----------
private val FondoPantalla = Color(0xFFFFF8EF)
private val FondoCard = Color(0xFFF3EBDD)
private val FondoLista = Color(0xFFF8F1E4)
private val Marron = Color(0xFF823B18)
private val MarronFecha = Color(0xFFA0522D)
private val VerdeOliva = Color(0xFF4F6B2F)
private val VerdeIndicador = Color(0xFFCDEB9B)
private val TextoOscuro = Color(0xFF2B2118)
private val TextoSecundario = Color(0xFF5A4E45)

@Composable
fun PerfilExplorador(navController: NavController, viewmodel: viewmodelUsuarios) {


    var mostrarDialogo by remember { mutableStateOf(false) }
    // Si no hay sesión (p. ej. tras cerrarSesion) no se dibuja nada
    val usuario = viewmodel.usuarioActivo ?: return

    // Datos del usuario:
    val nombre = usuario.nombre
    val ubicacion = usuario.direccion
    val nivel = "Nivel 0"
    val descripcion = "Descripcion del explorador"
    // Datos de la proxima experiencia:
    val dia = "DÍA"
    val numero = "0"
    val tituloEncuentro = "Titulo de encuentro"
    val lugarEncuentro = "Lugar de encuentro"

    Scaffold(
        containerColor = FondoPantalla,
        bottomBar = { BarraInferiorExplorador() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(FondoPantalla)
        ) {

            // ---------- Encabezado ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Perfil",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Image(
                    painter = painterResource(id = R.drawable.logoraices),
                    contentDescription = "Logo raíces",
                    modifier = Modifier.size(32.dp)
                )
            }
            HorizontalDivider(color = Color(0xFFEADFCB))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ---------- Foto con insignia de hoja ----------
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = painterResource(id = R.drawable.hombre_user),
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
                    text = descripcion,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    color = TextoSecundario,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // ---------- Botones editar / compartir ----------
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
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
                    IconButton(
                        onClick = { /* TODO: compartir perfil */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFE6DCCB), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Compartir",
                            tint = TextoOscuro,
                            modifier = Modifier.size(18.dp)
                        )
                    }
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

                // ---------- Opciones ----------
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FondoLista)
                ) {
                    Column {
                        OpcionPerfil(Icons.Outlined.Tune, "Preferencias de viaje") {
                            /* TODO: navController.navigate("preferenciasviaje") */
                        }
                        HorizontalDivider(color = Color(0xFFEFE5D2))
                        OpcionPerfil(Icons.Outlined.Shield, "Cuenta y privacidad") {
                            /* TODO: navController.navigate("cuentaprivacidad") */
                        }
                        HorizontalDivider(color = Color(0xFFEFE5D2))
                        OpcionPerfil(Icons.Outlined.VolunteerActivism, "Postular comunidad o taller") {
                            /* TODO: navController.navigate("postular") */
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
}

@Composable
private fun DialogoEditarPerfil(
    usuario: Usuario,
    onCancelar: () -> Unit,
    onGuardar: (Usuario) -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var telefono by remember { mutableStateOf(usuario.telefono) }
    var contraseña by remember { mutableStateOf(usuario.contraseña) }
    var direccion by remember { mutableStateOf(usuario.direccion) }
    var intereses by remember { mutableStateOf(usuario.intereses?.toList() ?: emptyList()) }
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

                Text(text = "Intereses", fontSize = 16.sp)
                InteresesChips(
                    intereses = intereses,
                    onInteresesChange = { intereses = it }
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
                        direccion.isBlank() || intereses.isEmpty()
                    ) {
                        error = "Debe llenar todos los campos"
                    } else {
                        onGuardar(
                            usuario.copy(
                                nombre = nombre.trim(),
                                telefono = telefono.trim(),
                                contraseña = contraseña,
                                direccion = direccion.trim(),
                                intereses = intereses.toMutableList()
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
private fun OpcionPerfil(icono: ImageVector, texto: String, onClick: () -> Unit) {
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
private fun BarraInferiorExplorador() {
    NavigationBar(containerColor = FondoPantalla) {

        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = TextoOscuro,
            selectedTextColor = TextoOscuro,
            indicatorColor = VerdeIndicador,
            unselectedIconColor = TextoOscuro,
            unselectedTextColor = TextoOscuro
        )

        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: navController.navigate("explorar") */ },
            icon = { Icon(Icons.Outlined.Explore, contentDescription = "Explorar") },
            label = { Text("Explorar", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
            colors = itemColors
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: navController.navigate("misviajes") */ },
            icon = { Icon(Icons.Outlined.Map, contentDescription = "Mis Viajes") },
            label = { Text("Mis Viajes", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
            colors = itemColors
        )
        NavigationBarItem(
            selected = true, // pantalla actual
            onClick = { },
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
            label = { Text("Perfil", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
            colors = itemColors
        )
    }
}
