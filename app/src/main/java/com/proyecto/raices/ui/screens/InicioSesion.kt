package com.proyecto.raices.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.R
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@Composable
fun InicioSesion(
    navController: NavController,
    viewmodel: viewmodelUsuarios
) {

    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }

    var tipoUsuario by remember {
        mutableStateOf("")
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDDD6CD))
            .padding(25.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF8EF)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logoraices),
                    contentDescription = "Logo Raíces"
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Conecta con la esencia de nuestra cultura",
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedButton(
                        onClick = {
                            tipoUsuario = "ANFITRION"
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (tipoUsuario == "ANFITRION") Color(0xFF823B18) else Color.Transparent,
                            contentColor = if (tipoUsuario == "ANFITRION") Color.White else Color.Black
                        )
                    ) {
                        Text("Anfitrión")
                    }

                    OutlinedButton(
                        onClick = {
                            tipoUsuario = "EXPLORADOR"
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (tipoUsuario == "EXPLORADOR") Color(0xFF823B18) else Color.Transparent,
                            contentColor = if (tipoUsuario == "EXPLORADOR") Color.White else Color.Black
                        )
                    ) {
                        Text("Explorador")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Correo electrónico",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                    },
                    placeholder = {
                        Text("ejemplo@correo.com")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF87736B),
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Contraseña",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = {
                        contraseña = it
                    },
                    label = {
                        Text("Ingresa Tu Contraseña")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF87736B),
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF823B18)
                    ),
                    onClick = {
                        // Edicion Mateo:
                        /*
                        val acceso =
                            viewmodel.login(
                                correo,
                                contraseña
                            )

                        mensaje =
                            if (acceso) {
                                val usuarioLogeado: Usuario? = viewmodel.buscarPorCorreo(correo)

                                if (usuarioLogeado != null) {
                                    val tipoUsuario = usuarioLogeado.tipoUsuario

                                    if (tipoUsuario == "ANFITRION") {
                                        navController.navigate("mis-servicios/$tipoUsuario") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                        "Inicio de sesión exitoso con ANFITRION"

                                    } else if (tipoUsuario == "EXPLORADOR"){
                                        navController.navigate("explorar/$tipoUsuario") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                        "Inicio de sesión exitoso con EXPLORADOR"
                                    } else {
                                        "Inicio de sesión exitoso con ADMIN"
                                    }
                                } else {
                                    "Inicio de sesión exitoso con ADMIN"
                                }
                            } else {
                                "Correo o contraseña incorrectos"
                         */
                        if (viewmodel.login(correo, contraseña, tipoUsuario)) {
                            val destino = if (tipoUsuario == "ANFITRION") "mis-servicios/${tipoUsuario}" else "explorar/${tipoUsuario}"
                            navController.navigate(destino) {
                                popUpTo("iniciosesion") { inclusive = true }
                            }
                        } else {
                            mensaje = "Correo o contraseña de usuario incorrectos"
                        }
                        //----------
                    }
                ) {
                    Text("Iniciar sesión")
                }

                if (mensaje.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = mensaje,
                        color = if (mensaje.contains("exitoso"))
                            Color(0xFF2E7D32)
                        else
                            Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row {

                    Text(
                        text = "¿No tienes una cuenta? "
                    )

                    Text(
                        text = "Regístrate aquí",
                        color = Color(0xFF823B18),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            if (tipoUsuario == "ANFITRION") {
                                navController.navigate("registroanfitrion")
                            } else if (tipoUsuario == "EXPLORADOR") {
                                navController.navigate("registroexplorador")
                            } else {
                                mensaje = "Seleccione un tipo de usuario"
                            }
                        }

                    )

                }
            }
        }
    }
}