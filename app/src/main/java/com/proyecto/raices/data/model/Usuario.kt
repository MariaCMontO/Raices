package com.proyecto.raices.data.model

data class Usuario(
    val nombre: String,
    val telefono: String,
    val correo:String,
    val contraseña:String,
    val direccion:String,
    val intereses: List<String>?,
    val oficio:String?,
    val tipoUsuario:String
)

