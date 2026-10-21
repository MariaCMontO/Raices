package com.proyecto.raices.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyecto.raices.ui.screens.InicioSesion
import com.proyecto.raices.ui.screens.PantallaAñadirServicio
import com.proyecto.raices.ui.screens.PantallaExplorar
import com.proyecto.raices.ui.screens.PantallaMisServicios
import com.proyecto.raices.ui.screens.PantallaMisViajes
import com.proyecto.raices.ui.screens.RegistroAnfitrion
import com.proyecto.raices.ui.screens.RegistroExplorador
import com.proyecto.raices.viewmodel.ServicioViewModel
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@Composable
fun AppNavigation(){
    val navController= rememberNavController()
    val viewmodel: viewmodelUsuarios= viewModel()

    val context = LocalContext.current
    val servicioVM: ServicioViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ServicioViewModel(
                    context.applicationContext as Application
                ) as T
            }
        }
    )

    NavHost(
        navController=navController,
        startDestination = "iniciosesion"
    ){
        composable("registroexplorador"){
            RegistroExplorador(navController, viewmodel)
        }
        composable("registroanfitrion"){
            RegistroAnfitrion(navController, viewmodel)
        }
        composable("iniciosesion"){
            InicioSesion(navController, viewmodel)
        }
        composable("explorar/{tipoUsuario}"){ backStackEntry ->
            val tipoUsuario = backStackEntry.arguments?.getString("tipoUsuario") ?: ""
            PantallaExplorar(navController, servicioVM, tipoUsuario)
        }
        composable("viajes/{tipoUsuario}"){ backStackEntry ->
            val tipoUsuario = backStackEntry.arguments?.getString("tipoUsuario") ?: ""
            PantallaMisViajes(navController, servicioVM, tipoUsuario)
        }
        composable("mis-servicios/{tipoUsuario}"){ backStackEntry ->
            val tipoUsuario = backStackEntry.arguments?.getString("tipoUsuario") ?: ""
            PantallaMisServicios(navController, servicioVM, tipoUsuario)
        }
        composable("añadir-servicio/{tipoUsuario}"){ backStackEntry ->
            val tipoUsuario = backStackEntry.arguments?.getString("tipoUsuario") ?: ""
            PantallaAñadirServicio(navController, servicioVM, tipoUsuario)
        }
    }
}