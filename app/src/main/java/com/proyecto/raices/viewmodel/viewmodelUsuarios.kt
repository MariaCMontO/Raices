package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.data.repository.UsuarioRepository
import kotlinx.coroutines.launch

class viewmodelUsuarios (application: Application): AndroidViewModel(application) {

    private val usuariosDao= DatabaseProvider
        .getDatabase(application)
        .usuariosDao()

    var listaUsuarios=usuariosDao.obtenerTodos()

    // Edicion Mateo:
    var usuarioActivo by mutableStateOf<Usuario?>(
        null
    )
        private set
    //---------

    fun agregarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            val usuarioExiste=usuariosDao.existeCorreo(usuario.correo)
            if (usuarioExiste) {
                return@launch;
            }
            usuariosDao.insertar(usuario)
        }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuariosDao.eliminar(usuario)
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
    fun actualizarPersona(usuarioActualizado: Usuario) {
        viewModelScope.launch {
            usuariosDao.actualizar(usuarioActualizado)
        }
    }
    // ----------------

    suspend fun buscarPorCorreo(correo:String): Usuario?{
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
        contraseña: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {

            usuarioActivo = usuariosDao.login(
                correo,
                contraseña
            )

            onResult(usuarioActivo != null)
        }
    }

    fun cerrarSesion () {
        usuarioActivo = null
    }
    //-----------
}