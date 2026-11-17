package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.Reserva
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReservaViewModel(application: Application) : AndroidViewModel(application) {

   // private val dao = DatabaseProvider
   //     .getDatabase(application)
    //   .reservaDao()
   private val api = RetrofitClient.api

    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    val reservas: StateFlow<List<Reserva>> = _reservas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    // CONSULTAR RESERVAS
    fun misReservas(correo: String) {
        viewModelScope.launch {
            _cargando.value = true

            try {
                _reservas.value = api.obtenerReservas(correo)
                _mensaje.value = "OK"

            } catch (e: Exception) {
                _mensaje.value = "Error al consultar reservas: ${e.message}"
            }

            _cargando.value = false
        }
    }

    // REGISTRAR RESERVA
    fun reservar(
        servicio: ServicioEntity,
        usuario: Usuario,
        fechaMillis: Long,
        personas: Int,
        notas: String
    ) {
        viewModelScope.launch {
            _cargando.value = true

            try {
                val respuesta = api.insertarReserva(
                    servicioId = servicio.id,
                    nombreServicio = servicio.nombre,
                    categoria = servicio.categoria.name,
                    nombreAnfitrion = servicio.nombreAnfitrion,
                    correoExplorador = usuario.correo,
                    fechaReserva = fechaMillis,
                    personas = personas,
                    precioUnitario = servicio.precio,
                    notas = notas.trim()
                )

                if (respuesta["mensaje"] == "OK") {
                    _mensaje.value = "Reserva realizada correctamente"
                    misReservas(usuario.correo)
                } else {
                    _mensaje.value= "No se pudo registrar la reserva"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error al reservar: ${e.message}"
            }

            _cargando.value = false
        }
    }

    // CANCELAR RESERVA
    fun cancelar(reserva: Reserva) {
        viewModelScope.launch {
            _cargando.value = true

            try {
                val respuesta = api.cancelarReserva(
                    id = reserva.id,
                    correoExplorador = reserva.correoExplorador
                )

                if (respuesta["mensaje"] == "OK") {
                    _mensaje.value = "Reserva cancelada correctamente"
                    misReservas(reserva.correoExplorador)
                } else {
                    _mensaje.value = "No se pudo cancelar la reserva"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error al cancelar: ${e.message}"
            }

            _cargando.value = false
        }
    }
}
