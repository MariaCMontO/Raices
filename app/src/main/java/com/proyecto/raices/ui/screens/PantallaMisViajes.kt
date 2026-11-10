package com.proyecto.raices.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.raices.ui.components.BarrasSuperiorInferior
import com.proyecto.raices.viewmodel.ServicioViewModel
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMisViajes(
    navController: NavController,
    servicioVM: ServicioViewModel,
    usuarioVM: viewmodelUsuarios
) {

    val usuario = usuarioVM.usuarioActivo ?: return
    val tipoUsuario = usuario.tipoUsuario

    BarrasSuperiorInferior(
        navController = navController,
        tipoUsuario = tipoUsuario

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("Mis Viajes")
        }
    }
}