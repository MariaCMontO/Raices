package com.proyecto.raices.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.raices.R
import com.proyecto.raices.viewmodel.viewmodelUsuarios
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navController: NavController,
    viewmodel: viewmodelUsuarios
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(3000)

        val usuario = viewmodel.usuarioActivo

        if (usuario != null) {

            val destino = if (usuario.tipoUsuario == "EXPLORADOR") "explorar" else "mis-servicios"

            navController.navigate(destino) {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("iniciosesion") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFFBF3E4),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFBF3E4))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoraices),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(180.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Logo App"
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Cargando aplicación...",
                    color = Color.Black
                )
            }
        }
    }
}