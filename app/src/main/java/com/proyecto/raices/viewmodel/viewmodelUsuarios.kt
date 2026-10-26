package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.data.sharedPreferences.SharedPreferencesManager
import kotlinx.coroutines.launch

class viewmodelUsuarios(application: Application) : AndroidViewModel(application) {

    private val sharedPrefManager = SharedPreferencesManager(application)

    private val usuariosDao = DatabaseProvider
        .getDatabase(application)
        .usuariosDao()

    var listaUsuarios = usuariosDao.obtenerTodos()

    // Edicion Mateo:
    var usuarioActivo by mutableStateOf<Usuario?>(
        null
    )
        private set

    init {
        viewModelScope.launch {
            val (correoGuardado, passGuardado) = sharedPrefManager.cargarDatos()
            if (correoGuardado.isNotEmpty() && passGuardado.isNotEmpty()) {

                usuarioActivo = usuariosDao.login(correoGuardado, passGuardado)
            }

            val cantidad = usuariosDao.obtenerCantidadUsuarios()
            if (cantidad == 0) {
                val listaUsuarios = mutableListOf<Usuario>(
                    Usuario(
                        nombre = "Anfitrión",
                        telefono = "123",
                        correo = "anfi@correo.com",
                        contraseña = "Anfi123",
                        direccion = "Calle 5 #14-98",
                        intereses = emptyList(),
                        oficio = "",
                        tipoUsuario = "ANFITRION",
                        sobreMi = "",
                        lenguas = mutableListOf(),
                        genero = ""
                    ),
                    Usuario(
                        nombre = "Explorador",
                        telefono = "123",
                        correo = "explo@correo.com",
                        contraseña = "Explo123",
                        direccion = "Calle 5 #14-98",
                        intereses = emptyList(), oficio = "",
                        tipoUsuario = "EXPLORADOR",
                        sobreMi = "",
                        lenguas = mutableListOf(),
                        genero = ""
                    ),
                    Usuario(
                        nombre = "ADMIN",
                        telefono = "123",
                        correo = "admin@correo.com",
                        contraseña = "Admin123",
                        direccion = "Calle 5 #14-98",
                        intereses = emptyList(),
                        oficio = "",
                        tipoUsuario = "ADMIN",
                        sobreMi = "",
                        lenguas = mutableListOf(),
                        genero = ""
                    ),
                )
                usuariosDao.insertarLista(listaUsuarios)
            }
        }
    }

    fun agregarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            val usuarioExiste = usuariosDao.existeCorreo(usuario.correo)
            if (usuarioExiste) {
                return@launch;
            }
            usuariosDao.insertar(usuario)
        }
    }

    /*--------------
    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuariosDao.eliminar(usuario)
        }
    }--------------
    */
    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuariosDao.eliminar(usuario)

            if (usuarioActivo?.correo == usuario.correo) {
                usuarioActivo = null
                sharedPrefManager.limpiarDatos()
            }
        }
    }

    // Edicion Mateo:
    /*
    fun actualizarPersona(usuarioActualizado: Usuario){
        UsuarioRepository.listaUsuarios = UsuarioRepository.listaUsuarios.map{
            if(it.correo== usuarioActualizado.correo) usuarioActualizado else it
        }.toMutableList() as ArrayList<Usuario>
        listaUsuarios= UsuarioRepository.listaUsuarios.toList()
    }
     */
    /*-----------
    fun actualizarPersona(usuarioActualizado: Usuario) {
        viewModelScope.launch {
            usuariosDao.actualizar(usuarioActualizado)

            if (usuarioActivo?.correo == usuarioActualizado.correo) {
                sharedPrefManager.guardarDatos(usuarioActualizado.correo, usuarioActualizado.contraseña)
            }
        }
    }-----------
    */

    fun actualizarPersona(usuarioActualizado: Usuario) {
        viewModelScope.launch {
            usuariosDao.actualizar(usuarioActualizado)

            if (usuarioActivo?.correo == usuarioActualizado.correo) {
                usuarioActivo = usuarioActualizado
                sharedPrefManager.guardarDatos(usuarioActualizado.correo, usuarioActualizado.contraseña)
            }
        }
    }

    suspend fun buscarPorCorreo(correo: String): Usuario? {
        return usuariosDao.obtenerPorCorreo(correo)
    }

    // Edicion Mateo:

    /*fun login(correo:String, contraseña:String): Boolean{
        val usuario=UsuarioRepository.listaUsuarios.find { it.correo== correo}
        if(usuario?.contraseña ==contraseña){
            return true
        }else{
            return false
        }
    }
     */
    fun login(
        correo: String,
        contrasena: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {

            usuarioActivo = usuariosDao.login(correo, contrasena)

            val exito = usuarioActivo != null

            if (exito) {
                sharedPrefManager.guardarDatos(correo, contrasena)
            }
            onResult(exito)
        }
    }

    fun cerrarSesion() {
        usuarioActivo = null
        sharedPrefManager.limpiarDatos()
    }
}