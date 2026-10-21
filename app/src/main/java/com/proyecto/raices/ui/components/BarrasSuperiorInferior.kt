package com.proyecto.raices.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proyecto.raices.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarrasSuperiorInferior(
    navController: NavController,
    tipoUsuario: String,
    content: @Composable (PaddingValues) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val botones = listOf(
        PantallaDestino.Explorar,
        PantallaDestino.MisViajes,
        PantallaDestino.MisServicios,
        PantallaDestino.AñadirServicio,
        PantallaDestino.Perfil
    )
    val destinoActual = botones.find { it.ruta == rutaActual }

    val tituloSuperior = destinoActual?.titulo ?: "Mi Aplicación"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp),
                        text = tituloSuperior,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFBF3E4),
                    titleContentColor = Color(0xFF000000)
                ),
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.logoraices),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(46.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            )
        },
        bottomBar = {
            AppBarraInferior(
                rutaActual = rutaActual,
                tipoUsuario = tipoUsuario,
                onPantallaSeleccionada = { pantalla ->
                    if (rutaActual != pantalla.ruta) {
                        navController.navigate(pantalla.ruta) {
//                            popUpTo("explorar-mis-servicios/${tipoUsuario}") {
//                                saveState = true
//                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        content(padding)
    }
}