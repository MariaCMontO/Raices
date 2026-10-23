package com.proyecto.raices.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(@PrimaryKey(autoGenerate = true)
    val id: Int = 0, val nombre: String, val telefono: String, val correo: String, val contraseña: String, val direccion: String, val intereses: List<String>?, val oficio: String?, val tipoUsuario: String, val sobreMi: String, val lenguas: List<String>, val genero: String)

