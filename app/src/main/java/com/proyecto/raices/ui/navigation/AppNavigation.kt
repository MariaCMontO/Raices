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
import com.proyecto.raices.ui.screens.PerfilAnfitrion
import com.proyecto.raices.ui.screens.PerfilExplorador
import com.proyecto.raices.ui.screens.PantallaAñadirServicio
import com.proyecto.raices.ui.screens.PantallaExplorar
import com.proyecto.raices.ui.screens.PantallaMisServicios
import com.proyecto.raices.ui.screens.PantallaMisViajes
import com.proyecto.raices.ui.screens.RegistroAnfitrion
import com.proyecto.raices.ui.screens.RegistroExplorador
import com.proyecto.raices.ui.screens.SplashScreen
import com.proyecto.raices.viewmodel.ServicioViewModel
import com.proyecto.raices.viewmodel.ViajesViewModel
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

    val viajesVM: ViajesViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViajesViewModel(
                    context.applicationContext as Application
                ) as T
            }
        }
    )


    NavHost(
        navController=navController,
        startDestination = "splash"
    ){
        composable("splash"){
            SplashScreen(navController, viewmodel)
        }
        composable("registroexplorador"){
            RegistroExplorador(navController, viewmodel)
        }
        composable("registroanfitrion"){
            RegistroAnfitrion(navController, viewmodel)
        }
        composable("iniciosesion"){
            InicioSesion(navController, viewmodel)
        }
        composable("perfilanfitrion") {
            PerfilAnfitrion(navController, viewmodel)
        }
        composable("perfilexplorador") {
            PerfilExplorador(navController, viewmodel)
        }
        composable("explorar"){
            PantallaExplorar(navController, servicioVM, viewmodel)
        }
        composable("viajes"){
            PantallaMisViajes(navController, viajesVM, viewmodel)
        }
        composable("mis-servicios"){
            PantallaMisServicios(navController, servicioVM, viewmodel)
        }
        composable("añadir-servicio"){
            PantallaAñadirServicio(navController, servicioVM, viewmodel)
        }
    }
}