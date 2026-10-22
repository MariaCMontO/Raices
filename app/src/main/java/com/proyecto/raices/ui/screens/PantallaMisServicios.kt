package com.proyecto.raices.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.raices.ui.components.BarrasSuperiorInferior
import com.proyecto.raices.ui.components.ItemServicio
import com.proyecto.raices.ui.components.chipsUnico
import com.proyecto.raices.viewmodel.ServicioViewModel
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMisServicios(
    navController: NavController,
    servicioVM: ServicioViewModel,
    usuarioVM: viewmodelUsuarios
) {

    var query by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    val usuario = usuarioVM.usuarioActivo ?: return
    val tipoUsuario = usuario.tipoUsuario

    val listaServicios = servicioVM.listaServiciosFiltrada

    LaunchedEffect(query, categoria) {
        servicioVM.aplicarBusqueda(query, categoria)
    }

    BarrasSuperiorInferior(
        navController = navController,
        tipoUsuario = tipoUsuario

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFf3f3f0)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            LazyColumn {

                items(listaServicios, key = { it.id }) { servicio ->
                    ItemServicio(
                        servicio = servicio,
                        onClick = {
                            //navController.navigate("detalle/${servicio.id}")
                        },
                        tipoUsuario = tipoUsuario
                    )
                }
            }
        }
    }
}