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

    fun agregarUsuario(usuario: Usuario) {
        val usuarioExiste=UsuarioRepository.listaUsuarios.find { it.correo== usuario.correo}
        if (usuarioExiste==null) {
            return;
        }
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