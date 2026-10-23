package com.proyecto.raices.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.data.repository.UsuarioRepository

class viewmodelUsuarios: ViewModel() {

    var listaUsuarios by mutableStateOf(
        UsuarioRepository.listaUsuarios.toList()
    )
        private set

    // Edicion Mateo:
    var usuarioActivo by mutableStateOf<Usuario?>(
        null
    )
        private set
    //---------

<<<<<<< Updated upstream
=======
    init {
        viewModelScope.launch {
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

>>>>>>> Stashed changes
    fun agregarUsuario(usuario: Usuario) {
        UsuarioRepository.listaUsuarios.add(usuario)
        listaUsuarios= UsuarioRepository.listaUsuarios.toList()
    }

    fun eliminarUsuario(usuario: Usuario) {
        UsuarioRepository.listaUsuarios.removeAll{
            it.correo==usuario.correo
        }
        listaUsuarios= UsuarioRepository.listaUsuarios.toList()
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
    fun actualizarPersona(usuarioActualizado: Usuario) {
        UsuarioRepository.listaUsuarios = UsuarioRepository.listaUsuarios
            .map { if (it.correo == usuarioActualizado.correo) usuarioActualizado else it }
            .toMutableList()
        listaUsuarios = UsuarioRepository.listaUsuarios.toList()

        if (usuarioActivo?.correo == usuarioActualizado.correo) {
            usuarioActivo = usuarioActualizado
        }
    }
    // ----------------

    fun buscarPorCorreo(correo:String): Usuario?{
    return UsuarioRepository.listaUsuarios.find { it.correo== correo }
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
    fun login(correo:String, contraseña:String, tipoUsuario: String): Boolean{
        val usuario=UsuarioRepository.listaUsuarios.find { it.correo== correo}
        if(usuario?.contraseña ==contraseña && usuario.tipoUsuario == tipoUsuario){
            usuarioActivo = usuario
            return true
        }else{
            usuarioActivo = null
            return false
        }
    }

    fun cerrarSesion () {
        usuarioActivo = null
    }
    //-----------
}