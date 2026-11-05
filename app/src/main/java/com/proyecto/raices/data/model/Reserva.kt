package com.proyecto.raices.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class Reserva(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val servicioId: Int,
    val nombreServicio: String,
    val categoria: CategoriaServicio,
    val nombreAnfitrion: String,
    val correoExplorador: String,
    val fechaReserva: Long,
    val personas: Int,
    val precioUnitario: Double,
    val total: Double,
    val notas: String
)
