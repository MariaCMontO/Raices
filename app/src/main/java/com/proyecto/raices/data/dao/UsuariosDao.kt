package com.proyecto.raices.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyecto.raices.data.model.Usuario

@Dao
interface UsuariosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): kotlinx.coroutines.flow.Flow<List<Usuario>>

    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun obtenerPorCorreo(correo: String): Usuario?

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Delete
    suspend fun eliminar(usuario: Usuario)

    @Query("""
        SELECT * FROM usuarios
        WHERE correo = :correo
        AND contraseña = :contrasena
        LIMIT 1
    """)
    suspend fun login(
        correo: String,
        contrasena: String
    ): Usuario?

    @Query("""
        SELECT EXISTS(
            SELECT 1
            FROM usuarios
            WHERE correo = :correo
        )
    """)
    suspend fun existeCorreo(correo: String): Boolean

}