package com.proyecto.raices.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.proyecto.raices.data.model.EstadoGrupo
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.MiembroGrupo
import kotlinx.coroutines.flow.Flow

@Dao
interface GrupoViajeDao {

    @Insert
    suspend fun insertarGrupo(grupo: GrupoViaje): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarMiembro(miembro: MiembroGrupo)

    @Insert
    suspend fun insertarMensaje(mensaje: MensajeGrupo)

    @Query("DELETE FROM miembros_grupo WHERE grupoId = :grupoId AND correoUsuario = :correo")
    suspend fun eliminarMiembro(grupoId: Int, correo: String)

    @Query("SELECT COUNT(*) FROM miembros_grupo WHERE grupoId = :grupoId")
    suspend fun contarMiembros(grupoId: Int): Int

    @Query("UPDATE grupos_viaje SET estado = :estado WHERE id = :grupoId")
    suspend fun actualizarEstado(grupoId: Int, estado: EstadoGrupo)

    @Query("SELECT COUNT(*) FROM grupos_viaje")
    suspend fun contarGrupos(): Int

    @Query(
        """
        SELECT g.* FROM grupos_viaje g
        INNER JOIN miembros_grupo m ON m.grupoId = g.id
        WHERE m.correoUsuario = :correo
        ORDER BY g.id DESC
        """
    )
    fun obtenerMisGrupos(correo: String): Flow<List<GrupoViaje>>


    @Query(
        """
        SELECT * FROM grupos_viaje
        WHERE id NOT IN (SELECT grupoId FROM miembros_grupo WHERE correoUsuario = :correo)
        ORDER BY id DESC
        """
    )
    fun obtenerOtrosGrupos(correo: String): Flow<List<GrupoViaje>>

    @Query("SELECT * FROM mensajes_grupo WHERE grupoId = :grupoId ORDER BY fecha ASC")
    fun obtenerMensajes(grupoId: Int): Flow<List<MensajeGrupo>>

    @Transaction
    suspend fun crearGrupoConCreador(grupo: GrupoViaje) {
        val id = insertarGrupo(grupo).toInt()
        insertarMiembro(MiembroGrupo(id, grupo.correoCreador))
    }

    @Transaction
    suspend fun unirseAGrupo(grupoId: Int, correo: String) {
        insertarMiembro(MiembroGrupo(grupoId, correo))
        if (contarMiembros(grupoId) >= 2) {
            actualizarEstado(grupoId, EstadoGrupo.ACTIVO)
        }
    }
}
