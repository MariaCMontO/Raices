package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.EstadoGrupo
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.MiembroGrupo
import com.proyecto.raices.data.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViajesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider
        .getDatabase(application)
        .grupoViajeDao()

    fun misGrupos(correo: String): Flow<List<GrupoViaje>> = dao.obtenerMisGrupos(correo)

    fun otrosGrupos(correo: String): Flow<List<GrupoViaje>> = dao.obtenerOtrosGrupos(correo)

    fun mensajes(grupoId: Int): Flow<List<MensajeGrupo>> = dao.obtenerMensajes(grupoId)

    init {

        viewModelScope.launch {
            if (dao.contarGrupos() == 0) {
                val ceramica = dao.insertarGrupo(
                    GrupoViaje(
                        nombre = "Ruta de la Cerámica de Ráquira",
                        descripcion = "Recorrido por los talleres de cerámica.",
                        estado = EstadoGrupo.ACTIVO,
                        correoCreador = "carlos@correo.com"
                    )
                ).toInt()
                dao.insertarMiembro(MiembroGrupo(ceramica, "carlos@correo.com"))
                dao.insertarMiembro(MiembroGrupo(ceramica, "explo@correo.com"))
                dao.insertarMensaje(
                    MensajeGrupo(
                        grupoId = ceramica,
                        correoAutor = "carlos@correo.com",
                        nombreAutor = "Don Carlos",
                        generoAutor = "M",
                        texto = "¿Llevamos sombrero para el recorrido?"
                    )
                )

                val tejidos = dao.insertarGrupo(
                    GrupoViaje(
                        nombre = "Tejidos de Guacamayas",
                        descripcion = "Visita a las artesanas de Boyacá.",
                        correoCreador = "carlos@correo.com"
                    )
                ).toInt()
                dao.insertarMiembro(MiembroGrupo(tejidos, "carlos@correo.com"))

                val quindio = dao.insertarGrupo(
                    GrupoViaje(
                        nombre = "Travesía en el Quindío",
                        descripcion = "Visita al Quindío.",
                        correoCreador = "carlos@correo.com"
                    )
                ).toInt()
                dao.insertarMiembro(MiembroGrupo(quindio, "carlos@correo.com"))
            }
        }
    }

    fun crearGrupo(nombre: String, descripcion: String, correoCreador: String) {
        if (nombre.isBlank()) return
        viewModelScope.launch {
            dao.crearGrupoConCreador(
                GrupoViaje(
                    nombre = nombre.trim(),
                    descripcion = descripcion.trim(),
                    correoCreador = correoCreador
                )
            )
        }
    }

    fun unirse(grupoId: Int, correo: String) {
        viewModelScope.launch { dao.unirseAGrupo(grupoId, correo) }
    }

    fun salir(grupoId: Int, correo: String) {
        viewModelScope.launch { dao.eliminarMiembro(grupoId, correo) }
    }

    fun enviarMensaje(grupoId: Int, usuario: Usuario, texto: String) {
        if (texto.isBlank()) return
        viewModelScope.launch {
            dao.insertarMensaje(
                MensajeGrupo(
                    grupoId = grupoId,
                    correoAutor = usuario.correo,
                    nombreAutor = usuario.nombre,
                    generoAutor = usuario.genero,
                    texto = texto.trim()
                )
            )
        }
    }
}
