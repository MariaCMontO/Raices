package com.proyecto.raices.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyecto.raices.data.model.ServicioEntity

@Dao
interface ServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(servicio: ServicioEntity)

    @Query("SELECT * FROM servicios")
    fun obtenerTodos(): kotlinx.coroutines.flow.Flow<List<ServicioEntity>>

    @Query("SELECT * FROM servicios WHERE id = :id")
    suspend fun obtenerPorId(id: String): ServicioEntity?

    @Query("SELECT * FROM servicios WHERE categoria = :categoria")
    suspend fun obtenerPorCategoria(categoria: String): ServicioEntity?

    @Query("SELECT * FROM servicios WHERE anfitrion = :anfitrion")
    suspend fun obtenerPorAnfitrion(anfitrion: String): ServicioEntity?

    @Update
    suspend fun actualizar(servicio: ServicioEntity)

    @Delete
    suspend fun eliminar(servicio: ServicioEntity)
}