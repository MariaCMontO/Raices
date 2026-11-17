package com.proyecto.raices.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.EstadoGrupo
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.MiembroGrupo
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViajesViewModel(application: Application) : AndroidViewModel(application) {

   // private val dao = DatabaseProvider
   //     .getDatabase(application)
    //    .grupoViajeDao()

    private val api = RetrofitClient.api

    private val _misGrupos = MutableStateFlow<List<GrupoViaje>>(emptyList())
    val misGrupos: StateFlow<List<GrupoViaje>> = _misGrupos

    private val _otrosGrupos = MutableStateFlow<List<GrupoViaje>>(emptyList())
    val otrosGrupos: StateFlow<List<GrupoViaje>> = _otrosGrupos

    private val _mensajesPorGrupo = MutableStateFlow<Map<Int, List<MensajeGrupo>>>(emptyMap())
    val mensajesPorGrupo: StateFlow<Map<Int, List<MensajeGrupo>>> = _mensajesPorGrupo

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun cargarGrupos(correo: String) {
        viewModelScope.launch {
            try {
                _misGrupos.value = api.obtenerMisGrupos(correo)
                _otrosGrupos.value = api.obtenerOtrosGrupos(correo)
            } catch (e: Exception) {
                Log.w("ViajesViewModel", "No se pudieron cargar los grupos: ${e.message}")
            }
        }
    }

    fun cargarMensajes(grupoId: Int) {
        viewModelScope.launch {
            try {
                val lista = api.obtenerMensajes(grupoId)
                _mensajesPorGrupo.update { it + (grupoId to lista) }
            } catch (e: Exception) {
                Log.w("ViajesViewModel", "No se pudieron cargar los mensajes: ${e.message}")
            }
        }
    }

    fun crearGrupo(nombre: String, descripcion: String, correoCreador: String) {
        if (nombre.isBlank()) return
        viewModelScope.launch {
            _cargando.value = true

            try {
                val respuesta = api.crearGrupo(
                    nombre = nombre.trim(),
                    descripcion = descripcion.trim(),
                    correoCreador = correoCreador
                )

                if (respuesta["mensaje"] == "OK") {
                    _mensaje.value = "Grupo creado correctamente"
                    cargarGrupos(correoCreador)
                } else {
                    _mensaje.value = "No se pudo crear el grupo"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error al crear el grupo: ${e.message}"
            }

            _cargando.value = false
        }
    }

    fun unirse(grupoId: Int, correo: String) {
        viewModelScope.launch {
            _cargando.value = true

            try {
                val respuesta = api.unirseGrupo(
                    grupoId = grupoId,
                    correo = correo
                )

                if (respuesta["mensaje"] == "OK") {
                    _mensaje.value = "Te uniste al grupo"
                    cargarGrupos(correo)
                } else {
                    _mensaje.value = "No se pudo unir al grupo"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error al unirse: ${e.message}"
            }

            _cargando.value = false
        }
    }

    fun salir(grupoId: Int, correo: String) {
        viewModelScope.launch {
            _cargando.value = true

            try {
                val respuesta = api.salirGrupo(
                    grupoId = grupoId,
                    correo = correo
                )

                if (respuesta["mensaje"] == "OK") {
                    _mensaje.value = "Saliste del grupo"
                    cargarGrupos(correo)
                } else {
                    _mensaje.value = "No se pudo salir del grupo"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error al salir: ${e.message}"
            }

            _cargando.value = false
        }
    }

    fun enviarMensaje(grupoId: Int, usuario: Usuario, texto: String) {
        if (texto.isBlank()) return
        viewModelScope.launch {
            _cargando.value = true

            try {
                val respuesta = api.enviarMensaje(
                    grupoId = grupoId,
                    correoAutor = usuario.correo,
                    nombreAutor = usuario.nombre,
                    generoAutor = usuario.genero,
                    texto = texto.trim()
                )

                if (respuesta["mensaje"] == "OK") {
                    cargarMensajes(grupoId)
                } else {
                    _mensaje.value = "No se pudo enviar el mensaje"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error al enviar el mensaje: ${e.message}"
            }

            _cargando.value = false
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    private companion object {
        const val TAG = "ViajesViewModel"
    }
}
