package com.proyecto.raices.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.ui.components.BarrasSuperiorInferior
import com.proyecto.raices.ui.components.DialogoCrearGrupo
import com.proyecto.raices.ui.components.GrupoActivoCard
import com.proyecto.raices.ui.components.OtroGrupoItem
import com.proyecto.raices.ui.components.ReservaItem
import com.proyecto.raices.ui.components.ViajesColores
import com.proyecto.raices.viewmodel.ReservaViewModel
import com.proyecto.raices.viewmodel.ViajesViewModel
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMisViajes(
    navController: NavController,
    viajesVM: ViajesViewModel,
    reservaVM: ReservaViewModel,
    usuarioVM: viewmodelUsuarios
) {
    val usuario = usuarioVM.usuarioActivo ?: return

    val flujoMisGrupos = remember(usuario.correo) { viajesVM.misGrupos(usuario.correo) }
    val flujoOtros = remember(usuario.correo) { viajesVM.otrosGrupos(usuario.correo) }
    val misGrupos by flujoMisGrupos.collectAsState(initial = emptyList())
    val otrosGrupos by flujoOtros.collectAsState(initial = emptyList())

    val flujoReservas = remember(usuario.correo) { reservaVM.misReservas(usuario.correo) }
    val reservas by flujoReservas.collectAsState(initial = emptyList())

    var mostrarDialogo by remember { mutableStateOf(false) }

    BarrasSuperiorInferior(
        navController = navController,
        tipoUsuario = usuario.tipoUsuario
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(ViajesColores.Fondo),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Grupos de Viaje",
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = ViajesColores.Marron
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { mostrarDialogo = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ViajesColores.Marron,
                        contentColor = androidx.compose.ui.graphics.Color.White
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.size(6.dp))
                    Text("Crear Grupo", fontWeight = FontWeight.Medium)
                }
            }

            if (misGrupos.isEmpty()) {
                item {
                    Text(
                        "Aún no perteneces a ningún grupo. Crea uno o únete a uno de abajo.",
                        color = androidx.compose.ui.graphics.Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(misGrupos, key = { "mio-${it.id}" }) { grupo ->
                    GrupoActivoCard(grupo = grupo, usuario = usuario, viajesVM = viajesVM)
                }
            }

            if (otrosGrupos.isNotEmpty()) {
                item {
                    Text(
                        text = "Otros Grupos",
                        fontFamily = FontFamily.Serif,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                items(otrosGrupos, key = { "otro-${it.id}" }) { grupo ->
                    OtroGrupoItem(
                        grupo = grupo,
                        onUnirse = { viajesVM.unirse(grupo.id, usuario.correo) }
                    )
                }
            }

            // ---- Mis Reservas (debajo de Otros Grupos) ----
            item {
                Text(
                    text = "Mis Reservas",
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (reservas.isEmpty()) {
                item {
                    Text(
                        "Aún no tienes reservas. Ve a Explorar y reserva una experiencia.",
                        color = androidx.compose.ui.graphics.Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(reservas, key = { "reserva-${it.id}" }) { reserva ->
                    ReservaItem(
                        reserva = reserva,
                        onCancelar = { reservaVM.cancelar(reserva) }
                    )
                }
            }
        }
    }

    if (mostrarDialogo) {
        DialogoCrearGrupo(
            onCancelar = { mostrarDialogo = false },
            onCrear = { nombre, descripcion ->
                viajesVM.crearGrupo(nombre, descripcion, usuario.correo)
                mostrarDialogo = false
            }
        )
    }
}
