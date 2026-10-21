package com.proyecto.raices.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class ServicioEntity (
    @PrimaryKey
    val id: String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val anfitrion: String,
    val precio: Double
)