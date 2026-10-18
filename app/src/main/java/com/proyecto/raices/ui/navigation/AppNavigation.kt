package com.proyecto.raices.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyecto.raices.ui.screens.InicioSesion
import com.proyecto.raices.ui.screens.PerfilAnfitrion
import com.proyecto.raices.ui.screens.PerfilExplorador
import com.proyecto.raices.ui.screens.RegistroAnfitrion
import com.proyecto.raices.ui.screens.RegistroExplorador
import com.proyecto.raices.viewmodel.viewmodelUsuarios

@Composable
fun AppNavigation(){
    val navController= rememberNavController()
    val viewmodel: viewmodelUsuarios= viewModel()

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
        composable("perfilanfitrion") {
            PerfilAnfitrion(navController, viewmodel)
        }
        composable("perfilexplorador") {
            PerfilExplorador(navController, viewmodel)
        }

    }
}