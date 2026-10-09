package com.proyecto.raices.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.data.repository.UsuarioRepository

class viewmodelUsuarios: ViewModel() {

    var listaUsuarios by mutableStateOf(
        UsuarioRepository.usuarios.toList()
    )
        private set

    fun agregarUsuario(usuario: Usuario) {
        UsuarioRepository.usuarios.add(usuario)
        listaUsuarios= UsuarioRepository.usuarios.toList()
    }

    fun eliminarUsuario(usuario: Usuario) {
        UsuarioRepository.usuarios.removeAll{
            it.correo==usuario.correo
        }
        listaUsuarios= UsuarioRepository.usuarios.toList()
    }

    fun actualizarPersona(usuarioActualizado: Usuario){
        UsuarioRepository.usuarios = UsuarioRepository.usuarios.map{
            if(it.correo== usuarioActualizado.correo) usuarioActualizado else it
        }.toMutableList() as ArrayList<Usuario>
        listaUsuarios= UsuarioRepository.usuarios.toList()
    }

    fun buscarPorCorreo(correo:String): Usuario?{
    return UsuarioRepository.usuarios.find { it.correo== correo }
    }

    fun login(correo:String, contraseña:String): Boolean{
        val usuario=UsuarioRepository.usuarios.find { it.correo== correo}
        if(usuario?.contraseña ==contraseña){
            return true
        }else{
            return false
        }
    }

}