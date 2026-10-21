package com.proyecto.raices.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(servicio: ServicioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(servicios: List<ServicioEntity>)

    @Query("SELECT * FROM servicios")
    fun obtenerTodos(): Flow<List<ServicioEntity>>

    @Query("SELECT * FROM servicios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): ServicioEntity?

    @Update
    suspend fun actualizar(servicio: ServicioEntity)

    @Delete
    suspend fun eliminar(servicio: ServicioEntity)

    @Query("""
        SELECT * FROM servicios 
        WHERE nombre LIKE '%' || :query || '%' 
           OR nombreAnfitrion LIKE '%' || :query || '%'
    """)
    suspend fun obtenerTodosPorNombreOAnfitrion(query: String): List<ServicioEntity>

    @Query("""
        SELECT * FROM servicios 
        WHERE  categoria = :categoria
    """)
    suspend fun obtenerTodosPorCategoria(categoria: CategoriaServicio): List<ServicioEntity>

    @Query("""
        SELECT * FROM servicios 
        WHERE (nombre LIKE '%' || :query || '%' OR nombreAnfitrion LIKE '%' || :query || '%') 
          AND categoria = :categoria
    """)
    suspend fun obtenerTodosPorTextoYCategoria(query: String, categoria: CategoriaServicio): List<ServicioEntity>

    @Query("SELECT * FROM servicios")
    suspend fun obtenerTodosAsList(): List<ServicioEntity>

    @Query("SELECT * FROM servicios WHERE correoAnfitrion = :correoAnfitrion ")
    suspend fun obtenerTodosPorCorreoAnfitrion(correoAnfitrion: String): List<ServicioEntity>

    @Query("SELECT COUNT(*) FROM servicios")
    suspend fun obtenerCantidadServicios(): Int
}