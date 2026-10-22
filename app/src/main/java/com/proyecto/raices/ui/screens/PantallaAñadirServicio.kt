package com.proyecto.raices.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

@Composable
fun PantallaAñadirServicio(
    navController: NavController,
    servicioVM: ServicioViewModel,
    usuarioVM: viewmodelUsuarios
) {
    val lista by servicioVM.listaServicios.collectAsState(initial = emptyList())

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

            Text("Añadir Servicio")
        }
    }
}
