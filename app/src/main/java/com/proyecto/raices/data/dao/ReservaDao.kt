package com.proyecto.raices.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.proyecto.raices.data.model.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {

    @Insert
    suspend fun insertar(reserva: Reserva)

    @Query("DELETE FROM reservas WHERE id = :id")
    suspend fun eliminarPorId(id: Int)

    @Query("SELECT * FROM reservas WHERE correoExplorador = :correo ORDER BY fechaReserva ASC")
    fun obtenerPorExplorador(correo: String): Flow<List<Reserva>>
}
