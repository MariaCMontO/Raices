package com.proyecto.raices.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class ServicioEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val descripcion: String,
    val categoria: CategoriaServicio,
    val nombreAnfitrion: String,
    val correoAnfitrion: String,
    val precio: Double
)