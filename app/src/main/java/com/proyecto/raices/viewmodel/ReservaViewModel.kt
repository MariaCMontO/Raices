package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.Reserva
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.data.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReservaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider
        .getDatabase(application)
        .reservaDao()

    fun misReservas(correo: String): Flow<List<Reserva>> = dao.obtenerPorExplorador(correo)

    fun reservar(
        servicio: ServicioEntity,
        usuario: Usuario,
        fechaMillis: Long,
        personas: Int,
        notas: String
    ) {
        viewModelScope.launch {
            dao.insertar(
                Reserva(
                    servicioId = servicio.id,
                    nombreServicio = servicio.nombre,
                    categoria = servicio.categoria,
                    nombreAnfitrion = servicio.nombreAnfitrion,
                    correoExplorador = usuario.correo,
                    fechaReserva = fechaMillis,
                    personas = personas,
                    precioUnitario = servicio.precio,
                    total = servicio.precio * personas,
                    notas = notas.trim()
                )
            )
        }
    }

    fun cancelar(reserva: Reserva) {
        viewModelScope.launch { dao.eliminarPorId(reserva.id) }
    }
}
