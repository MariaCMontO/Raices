package com.proyecto.raices.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class EstadoGrupo(val etiqueta: String) {
    PLANEANDO("Planeando"),
    ACTIVO("Activo")
}

@Entity(tableName = "grupos_viaje")
data class GrupoViaje(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val estado: EstadoGrupo = EstadoGrupo.PLANEANDO,
    val correoCreador: String
)

@Entity(
    tableName = "miembros_grupo",
    primaryKeys = ["grupoId", "correoUsuario"]
)
data class MiembroGrupo(
    val grupoId: Int,
    val correoUsuario: String
)

@Entity(tableName = "mensajes_grupo")
data class MensajeGrupo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val grupoId: Int,
    val correoAutor: String,
    val nombreAutor: String,
    val generoAutor: String,
    val texto: String,
    val fecha: Long = System.currentTimeMillis()
)
