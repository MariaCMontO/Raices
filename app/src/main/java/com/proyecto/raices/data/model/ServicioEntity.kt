package com.proyecto.raices.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "servicios")
data class ServicioEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val descripcion: String,
    val categoria: CategoriaServicio,

    @SerializedName("nombre_anfitrion")
    val nombreAnfitrion: String,

    @SerializedName("correo_anfitrion")
    val correoAnfitrion: String,

    val precio: Double
)